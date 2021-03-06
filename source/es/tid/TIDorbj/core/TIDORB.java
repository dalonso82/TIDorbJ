/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 478 $
* Date: $Date: 2011-04-29 16:42:47 +0200 (Fri, 29 Apr 2011) $
* Last modified by: $Author: avega $
*
* (C) Copyright 2004 Telef�nica Investigaci�n y Desarrollo
*     S.A.Unipersonal (Telef�nica I+D)
*
* Info about members and contributors of the MORFEO project
* is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/CREDITS
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*
* If you want to use this software an plan to distribute a
* proprietary application in any way, and you are not licensing and
* distributing your source code under GPL, you probably need to
* purchase a commercial license of the product.  More info about
* licensing options is available at:
*
*   http://www.morfeo-project.org/TIDorbJ/Licensing
*/    
package es.tid.TIDorbj.core;

import java.io.IOException;

import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_PERMISSION;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.ServiceInformationHolder;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UNKNOWN;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.portable.Delegate;
import org.omg.PortableServer.ForwardRequest;

import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.comm.CommunicationDelegate;
import es.tid.TIDorbj.core.comm.CommunicationException;
import es.tid.TIDorbj.core.comm.CommunicationManager;
import es.tid.TIDorbj.core.comm.iiop.IIOPCorbaloc;
import es.tid.TIDorbj.core.iop.IOR;
import es.tid.TIDorbj.core.messaging.AMIManager;
import es.tid.TIDorbj.core.poa.POAKey;
import es.tid.TIDorbj.core.typecode.AbstractInterfaceTypeCode;
import es.tid.TIDorbj.core.typecode.AliasTypeCode;
import es.tid.TIDorbj.core.typecode.ArrayTypeCode;
import es.tid.TIDorbj.core.typecode.EnumTypeCode;
import es.tid.TIDorbj.core.typecode.ExceptionTypeCode;
import es.tid.TIDorbj.core.typecode.FixedTypeCode;
import es.tid.TIDorbj.core.typecode.NativeTypeCode;
import es.tid.TIDorbj.core.typecode.ObjectRefTypeCode;
import es.tid.TIDorbj.core.typecode.RecursiveTypeCode;
import es.tid.TIDorbj.core.typecode.SequenceTypeCode;
import es.tid.TIDorbj.core.typecode.StringTypeCode;
import es.tid.TIDorbj.core.typecode.StructTypeCode;
import es.tid.TIDorbj.core.typecode.TypeCodeFactory;
import es.tid.TIDorbj.core.typecode.UnionTypeCode;
import es.tid.TIDorbj.core.typecode.ValueBoxTypeCode;
import es.tid.TIDorbj.core.typecode.ValueTypeCode;
import es.tid.TIDorbj.core.typecode.WStringTypeCode;
import es.tid.TIDorbj.core.util.Corbaloc;
import es.tid.TIDorbj.core.util.Corbaname;
import es.tid.TIDorbj.core.util.InitialReference;
import es.tid.TIDorbj.util.CircularTraceFile;
import es.tid.TIDorbj.util.Trace;

/**
 * TIDorb GIOPVersion 2.6 CORBA ORB.
 * <p>
 * 
 * @autor Juan A. C&aacute;ceres
 * @version 2.0
 */
public class TIDORB extends org.omg.CORBA_2_5.ORB
{

    /**
     * ORB GIOPVersion
     */

    public final static String st_version = "6.5.0rc1";

    /**
     * @return the ORB Singleton instance.
     */
    public static org.omg.CORBA.ORB init()
    {
        return SingletonORB.init();
    }

    /**
     * ORB configuration.
     */

    public ConfORB m_conf;

    /**
     * Local POAManagers.
     */
    public java.util.Vector m_POAManagers;

    /**
     * TraceService reference.
     */
    public es.tid.TIDorbj.util.Trace m_trace;

    /**
     * Local Codec factory.
     */
    private CodecFactoryImpl m_codec_factory;

    /** TODO: remove me
     * ORB Communication layer, drives request to the local layer or the
     * external layer.
     
    private es.tid.TIDorbj.core.comm.iiop.CommLayer m_comm_layer;*/
    
    /**
     * TODO: remove CommLayer usage and migrate to CommunicationManager
     */
    private CommunicationManager communicationManager;
    

    /**
     * Local CurrentImpl.
     */
    private es.tid.TIDorbj.core.poa.CurrentImpl m_current;

    /**
     * The ORB has been destroyed.
     */

    private boolean m_destroyed;

    /**
     * Local DynAny factory.
     */
    private es.tid.TIDorbj.dynAny.DynAnyFactoryImpl m_dyn_factory;

    /**
     * ORB PolicyManager.
     */
    private es.tid.TIDorbj.core.policy.PolicyManagerImpl m_orb_policy_manager;

    /**
     * ORB Services
     */

    private ORBServices m_orb_services;

    /**
     * Thread Policy Current.
     */
    private es.tid.TIDorbj.core.policy.PolicyCurrentImpl m_policy_current;


    /**
     * Compression Manager.
     */
    private es.tid.TIDorbj.core.compression.CompressionManagerImpl m_compression_manager;


    /**
     * Local rootPOA.
     */
    private es.tid.TIDorbj.core.poa.POAImpl m_root_POA;
   
    /**
     * Processing state: controls the ORB shutdown.
     */

    private ProcessingState m_state;

    /**
     * Thread Policy Context Manager.
     */
    private es.tid.TIDorbj.core.policy.PolicyContextManager 
    	m_thread_policy_context_manager;

    /**
	 * Maintains the order which the request was created
	 */
	private RequestCounter requestCounter;
	
    /**
     * TypeCode Cache.
     */

    private es.tid.TIDorbj.core.typecode.TypeCodeCache m_typecode_cache;

    /**
     * ORB ValueFactoryList
     */

    private java.util.Hashtable m_value_factories;

    /**
     * ORB Name
     */

    String m_orb_name;
    
    /**
     * AMI Manager
     */

    es.tid.TIDorbj.core.messaging.AMIManager m_ami_manager;
    

    /**
     * Constructor. The orb must be initialized using the set_parameters().
     */
    public TIDORB()
    {
        m_conf = null;
        m_state = new ProcessingState(this);
        m_destroyed = false;

        this.communicationManager = null;        
        this.requestCounter = new RequestCounter();
        
        m_dyn_factory = null;
        m_root_POA = null;
        m_trace = null;
        m_current = null;
        m_typecode_cache = null;
        m_ami_manager = null;
    }

    public void connect(org.omg.CORBA.Object obj)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    
    
    public TypeCode create_abstract_interface_tc(String id, String name)
    {
       return new AbstractInterfaceTypeCode(id, name);
    }
    
    public org.omg.CORBA.TypeCode 
    	create_alias_tc( String id,
    	                 String name,
    	                 org.omg.CORBA.TypeCode original_type)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null) || (original_type == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new AliasTypeCode(id, name, original_type);
    }

    public org.omg.CORBA.Any create_any()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return new es.tid.TIDorbj.core.AnyImpl(this);
    }

    public org.omg.CORBA.TypeCode 
    	create_array_tc( int length,
    	                 org.omg.CORBA.TypeCode element_type)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (length < 0)
            throw new BAD_PARAM("Illegal array length " + length);

        if (element_type == null)
            throw new BAD_PARAM("Null TypeCode reference");

        return new ArrayTypeCode(element_type, length);
    }

    public org.omg.CORBA.ContextList create_context_list()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return new es.tid.TIDorbj.core.ContextListImpl();
    }

    public org.omg.CORBA.TypeCode create_enum_tc(String id, String name,
                                                 String[] members)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null) || (members == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new EnumTypeCode(id, name, members);
    }

    public org.omg.CORBA.Environment create_environment()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return new es.tid.TIDorbj.core.EnvironmentImpl();
    }

    public org.omg.CORBA.ExceptionList create_exception_list()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return new es.tid.TIDorbj.core.ExceptionListImpl();
    }

    public org.omg.CORBA.TypeCode 
    	create_exception_tc( java.lang.String id,
    	                     java.lang.String name,
    	                     org.omg.CORBA.StructMember[] members)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null) || (members == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new ExceptionTypeCode(id, name, members);
    }

    public org.omg.CORBA.TypeCode create_fixed_tc(short digits, short scale)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return new FixedTypeCode(digits, scale);
    }

    public org.omg.CORBA.TypeCode create_interface_tc(String id, String name)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new ObjectRefTypeCode(id, name);
    }

    public org.omg.CORBA.NVList create_list(int count)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (count < 0)
            throw new BAD_PARAM("Count < 0", 0, CompletionStatus.COMPLETED_NO);

        return new es.tid.TIDorbj.core.NVListImpl(this, count);
    }

    public org.omg.CORBA.NamedValue create_named_value(String s,
                                                       org.omg.CORBA.Any any,
                                                       int flags)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((s == null) || (any == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return es.tid.TIDorbj.core.NamedValueImpl.from_int(flags, s, any);
    }

    public org.omg.CORBA.TypeCode create_native_tc(String id, String name)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new NativeTypeCode(id, name);
    }

    public org.omg.CORBA.NVList create_operation_list(org.omg.CORBA.Object oper)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public org.omg.CORBA.portable.OutputStream create_output_stream()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return new CDROutputStream(this, m_conf.block_size);
    }

    public org.omg.CORBA.Policy create_policy(int type, org.omg.CORBA.Any val)
        throws org.omg.CORBA.PolicyError
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (val == null)
            throw new BAD_PARAM("Null any reference");

        return es.tid.TIDorbj.core.policy.PolicyFactory.createPolicy(type, val);
    }

    /**
     * @deprecated
     */
    public org.omg.CORBA.TypeCode create_recursive_sequence_tc(int bound,
                                                               int offset)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public org.omg.CORBA.TypeCode create_recursive_tc(String id)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (id == null)
            throw new BAD_PARAM("Null String reference");

        return new RecursiveTypeCode(id);
    }

    public org.omg.CORBA.TypeCode 
    	create_sequence_tc( int bound,
    	                    org.omg.CORBA.TypeCode element_type)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (bound < 0)
            throw new BAD_PARAM("Illegal sequence length " + bound);

        if (element_type == null)
            throw new BAD_PARAM("Null TypeCode reference");

        return new SequenceTypeCode(element_type, bound);
    }

    public org.omg.CORBA.TypeCode create_string_tc(int bound)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (bound < 0)
            throw new BAD_PARAM("Illegal string length " + bound);

        return new StringTypeCode(bound);
    }

    public org.omg.CORBA.TypeCode 
    	create_struct_tc( String id,
    	                  String name,
    	                  org.omg.CORBA.StructMember[] members)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null) || (members == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new StructTypeCode(id, name, members);
    }

    public org.omg.CORBA.TypeCode 
    	create_union_tc( String id,
    	                 String name,
    	                 org.omg.CORBA.TypeCode discriminator_type,
    	                 org.omg.CORBA.UnionMember[] members)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null) || (discriminator_type == null)
            || (members == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new UnionTypeCode(id, name, discriminator_type, members);
    }

    public org.omg.CORBA.TypeCode 
    	create_value_box_tc( String id,
    	                     String name,
    	                     org.omg.CORBA.TypeCode boxed_type)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null) || (boxed_type == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new ValueBoxTypeCode(id, name, boxed_type);
    }

    public org.omg.CORBA.TypeCode 
    	create_value_tc( String id,
    	                 String name,
    	                 short type_modifier,
    	                 org.omg.CORBA.TypeCode concrete_base,
    	                 org.omg.CORBA.ValueMember[] members)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if ((id == null) || (name == null) || (concrete_base == null)
            || (members == null))
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return new ValueTypeCode(id, name, type_modifier, concrete_base,
                                 members);
    }

    public org.omg.CORBA.TypeCode create_wstring_tc(int bound)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (bound < 0)
            throw new BAD_PARAM("Illegal string length " + bound);

        return new WStringTypeCode(bound);
    }

    /**
     * Free all the ORB resources: connections and threads.
     */

    synchronized public void destroy()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        // The thread is serving a request?

        if (initPOACurrent().inContext()) { // yes, it is in the serving request
            // context

            throw new BAD_INV_ORDER(3, CompletionStatus.COMPLETED_NO);
        }

        m_state.shutdown();

        m_state.waitForShutdown();

        /**
         * TODO: remove CommLayer usage and migrate to CommunicationManager
         * m_comm_layer.destroy();
         */
        this.communicationManager.destroy();
        this.communicationManager = null;
        

        if (m_trace != null) {
            try {
                printTrace(Trace.USER, "Closing ORB Session.");
                m_trace.close();
            }
            catch (Throwable th) {}

            m_trace = null;
        }

        if (m_value_factories != null)
            m_value_factories.clear();

        m_value_factories = null;

        if (m_codec_factory != null) {
            m_codec_factory.destroy();
            m_codec_factory = null;
        }

        if (m_dyn_factory != null) {
            m_dyn_factory.destroy();
            m_dyn_factory = null;
        }

        m_orb_services.destroy();
        m_orb_services = null;

        m_root_POA = null;

        if (m_current != null) {
            m_current.destroy();
            m_current = null;
        }

        m_destroyed = true;
    }

    public boolean destroyed()
    {
        return m_destroyed;
    }

    public void disconnect(org.omg.CORBA.Object obj)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * @deprecated
     */
    public org.omg.CORBA.Current get_current()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_state.isShutdowned())
            throw new BAD_INV_ORDER(4, CompletionStatus.COMPLETED_NO);

        return m_current;
    }

    public org.omg.CORBA.Context get_default_context()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public org.omg.CORBA.Request get_next_response()
        throws org.omg.CORBA.WrongTransaction
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public org.omg.CORBA.TypeCode get_primitive_tc(org.omg.CORBA.TCKind tcKind)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (tcKind == null)
            throw new BAD_PARAM("Null TCKind reference", 0,
                                CompletionStatus.COMPLETED_NO);

        return TypeCodeFactory.getBasicTypeCode(tcKind);
    }

    public boolean 
    	get_service_information( short service_type,
    	                         ServiceInformationHolder service_info)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    // corba 2.3 operations

    // always return a ValueDef or throw BAD_PARAM if not repid of a value
    public org.omg.CORBA.Object get_value_def(String repid)
        throws org.omg.CORBA.BAD_PARAM
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * TODO: remove CommLayer usage and migrate to CommunicationManager
     */
    public CommunicationManager getCommunicationManager(){
    	if ( m_destroyed ){
    		throw new OBJECT_NOT_EXIST();
    	}
    	return this.communicationManager;
    }
    
    

    public es.tid.TIDorbj.core.policy.PolicyContextManager 
    	getPolicyContextManager()
    {
        return m_thread_policy_context_manager;
    }

    public es.tid.TIDorbj.core.policy.PolicyManagerImpl getPolicyManager()
    {
        return initPolicyManager();
    }

    public es.tid.TIDorbj.core.compression.CompressionManagerImpl getCompressionManager()
    {
        return initCompressionManager();
    }

    public es.tid.TIDorbj.core.typecode.TypeCodeCache getTypeCodeCache()
    {
        return m_typecode_cache;
    }
    
    public RequestCounter getRequestCounter()
	{
	    return this.requestCounter;
	}
    

    // CORBA 2.5
    public String id()
    {
        return m_conf.orb_id;
    }

    // ORB Services

    public es.tid.TIDorbj.core.poa.POAImpl initPOA() {
        synchronized (this) {
            if (m_root_POA == null) {
                m_root_POA = 
                    es.tid.TIDorbj.core.poa.POAImpl.createRootPOA(this);

                
                // init the orb listening points (usually one for each layer)
                try {
                    this.communicationManager.setServerModeEnabled( true );
                } catch ( CommunicationException ce ) {
                    throw new INITIALIZE( "Unable to activate server mode: " + ce.getMessage() );
                }
                
                m_state.running();
            }
        }

        return m_root_POA;
    }

    public es.tid.TIDorbj.core.poa.CurrentImpl initPOACurrent()
    {
        synchronized (this) {
            if (m_current == null) {
                m_current = new es.tid.TIDorbj.core.poa.CurrentImpl(this);
            }
        }

        return m_current;
    }

    public String[] list_initial_services()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        return m_orb_services.listInitialServices();
    }

    public org.omg.CORBA.portable.ValueFactory lookup_value_factory(String id)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);
        return (org.omg.CORBA.portable.ValueFactory) m_value_factories.get(id);
    }

    public String object_to_string(org.omg.CORBA.Object obj)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (obj == null)
            return IOR.nullIOR().toString();

        org.omg.CORBA.portable.Delegate delegate = 
            ((org.omg.CORBA.portable.ObjectImpl) obj)
                                                                                            ._get_delegate();

        if (obj instanceof org.omg.CORBA.LocalObject)
            throw new MARSHAL("Impossible to marshall a local object.",
                              4,
                              CompletionStatus.COMPLETED_NO);

        if (delegate instanceof CommunicationDelegate)
            return ((CommunicationDelegate) delegate).toString();
        else
            throw new BAD_PARAM("This Object has not been create by TIDorb",
                                0,
                                CompletionStatus.COMPLETED_NO);
    }

    public String objectToURL(org.omg.CORBA.Object obj)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (obj == null)
            return Corbaloc.toURL(IOR.nullIOR());

        org.omg.CORBA.portable.Delegate delegate =
            ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate();

        if (delegate instanceof CommunicationDelegate) {
            //TODO: refactor URL/String IOR stuff
            return ((CommunicationDelegate) delegate).getReference().toURL();
        } else
            throw new BAD_PARAM("This Object has not been create by TIDorb", 0,
                                CompletionStatus.COMPLETED_NO);
    }

    public void perform_work()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_state.isShutdowned())
            throw new BAD_INV_ORDER(4, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public boolean poll_next_response()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void printTrace(int level, String message)
    {
        if (m_trace != null)
            m_trace.print(level, message);
    }

    public void printTrace(int level, String message, Throwable e)
    {
        if (m_trace != null)
            m_trace.printStackTrace(level, message, e);
    }

    public void printTrace(int level, String[] message)
    {
        if (m_trace != null)
            m_trace.print(level, message);
    }

    public void printDump(int level, byte[] message, int length)
    {
        if (m_trace != null)
            m_trace.dump(level, message, length);
    }

    public void register_initial_reference(String object_name,
                                           org.omg.CORBA.Object obj)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        m_orb_services.registerInitialReference(object_name, obj);
    }

    public org.omg.CORBA.portable.ValueFactory 
    	register_value_factory( String id,
    	                        org.omg.CORBA.portable.ValueFactory factory)
    {

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        synchronized (m_value_factories) {
            if (m_value_factories.containsKey(id))
                return null;
            m_value_factories.put(id, factory);
        }

        return factory;
    }

    public void removeInitialReference(String object_name)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        m_orb_services.removeInitialReference(object_name);
    }

    public org.omg.CORBA.Object resolve_initial_references(String object_name)
        throws org.omg.CORBA.ORBPackage.InvalidName
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (object_name == null)
            throw new BAD_PARAM("Null String reference", 24,
                                CompletionStatus.COMPLETED_NO);

        return m_orb_services.resolveService(object_name);
    }

    public void run()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_state.isShutdowned())
            throw new BAD_INV_ORDER(4, CompletionStatus.COMPLETED_NO);

        if (m_root_POA == null) {
            throw new org.omg.CORBA.INTERNAL("RootPOA has not been created.");
        }
        
        m_state.waitForShutdown();
    }

    public void send_multiple_requests_deferred(org.omg.CORBA.Request[] req)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    public void send_multiple_requests_oneway(org.omg.CORBA.Request[] req)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        for (int i = 0; i < req.length; i++)
            req[i].send_oneway();
    }

    public void set_delegate(java.lang.Object wrapper)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_state.isShutdowned())
            throw new BAD_INV_ORDER(4, CompletionStatus.COMPLETED_NO);

        if (wrapper instanceof org.omg.PortableServer.Servant) {
            org.omg.PortableServer.Servant servant =
                (org.omg.PortableServer.Servant) wrapper;

            if (m_root_POA == null) {
                initPOA();
            }

            try {
                org.omg.CORBA.ORB servantOrb = servant._orb();
                if ( servantOrb != this ) {
                    throw new 
                    	BAD_PARAM("Servant is already active in another ORB");
                }
            }
            catch (org.omg.CORBA.BAD_INV_ORDER e) {
                try {
                    m_root_POA.activate_object(servant);

                    // Exceptions should never be thrown
                } catch (org.omg.PortableServer.POAPackage.WrongPolicy wp)
                {
                }
                catch (org.omg.PortableServer.POAPackage.ServantAlreadyActive s)
                {
                }
            }
        } else
            throw new BAD_PARAM("Servant expected");

    }

    public void shutdown(boolean wait_for_completion)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_state.isShutdowned())
            throw new BAD_INV_ORDER(4, CompletionStatus.COMPLETED_NO);

        m_state.shutdown();

        if (wait_for_completion) {
            if (initPOACurrent().inContext()) {
                // yes, it is in the serving request context
                throw new BAD_INV_ORDER(3, CompletionStatus.COMPLETED_NO);
            }

            m_state.waitForShutdown();
        }
    }

    public org.omg.CORBA.Object string_to_object(String str)
    {

        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (str == null) {
            throw new BAD_PARAM("Null String reference", 0,
                                CompletionStatus.COMPLETED_NO);
        }

        if (str.startsWith("corbaname:"))
            return corbanameUrlToObject(str);

        if (str.startsWith("file://"))
            return fileUrlToObject(str);

        if (str.startsWith("corbaloc:rir:"))
            return rirUrlToObject(str);

        if (str.startsWith("corbaloc:tidorbj:"))
            return tidorbjUrlToObject(str);
          

        Delegate delegate;
        try {
            delegate = this.communicationManager.createDelegate( str );
        } catch ( CommunicationException ce){
            delegate = null;
        }
        if ( delegate != null ){
            ObjectImpl o = new ObjectImpl();
            o._set_delegate( delegate );
            return o;
        } else {
            throw new BAD_PARAM( 
                "UNKNOWN String reference", 0, CompletionStatus.COMPLETED_NO
            );
        }
        /*
        return iorToObject(str);
        */
    }
    
    /*
     * PRIVATE METHODS
     * */
    public POAKey resolvePOAKey( ObjectKey objectKey ) 
    	throws ForwardRequest
    {
        if( objectKey != null ){
            //not initialized to check every condition against compiler :)
            POAKey key;
            try {
                /*
                 * Usually successfull for local requests, because objectKey 
                 * comes directly from an IOR. We shouldn't know this right here
                 */
                if ( objectKey instanceof POAKey ){
                    key = ( POAKey )objectKey;
                } else {
                    key = POAKey.createKey( objectKey.getMarshaledKey() );
                }
            } catch (Throwable th) {
                key = null;
            }
            
            if(key != null) {
                return key;
            }           
            
            
            /*
             * If objectKey comes from a remote request, it must be resolved
             * through the ORB.
             * First: try to get the URL, and after that, resolve it against
             * registered initial references
             */
            String urlKey = objectKey.getURL();
            org.omg.CORBA.Object reference = null;
            	
            if ( urlKey != null ) {                    
                
                if ( urlKey != null ) {
                   
                        String initialReference;
                        try {
                            initialReference = 
                                this.communicationManager.getInitialReference( 
                                     urlKey 
                                );
                        } catch ( CommunicationException ce ){
                        	/*
                        	if ( m_trace != null ){
                                m_trace.print( 
                                    Trace.ERROR,
                                    new String[]{
                                        "Unable to resolve url reference: ",
                                        urlKey, " ", ce.getMessage()
                                    }
                                );
                            }
                            */
                            initialReference = null;
                        }//initialReference retrieval                            
                        
                        if(initialReference == null) {
                        	initialReference = urlKey;
                        }
                        
                        try {
                            reference = resolve_initial_references( 
                                initialReference
                            );
                        } catch ( Throwable th ){
                            if ( m_trace != null ){
                                m_trace.print( 
                                    Trace.ERROR,
                                    new String[]{
                                        "Unable to resolve initial reference reference: ",
                                        initialReference, " ", th.getMessage()
                                    }
                                );
                            }
                            reference = null;
                        }
                        if ( reference != null ) {
                            if ( reference instanceof IOR ){
                                objectKey = (( IOR )reference ).getObjectKey();
                            } else {
                                org.omg.CORBA.portable.ObjectImpl ref =
                                    (org.omg.CORBA.portable.ObjectImpl)reference;
                                CommunicationDelegate delegate =
                                    ( CommunicationDelegate ) ref._get_delegate(); 
                                objectKey = delegate.getReference().getObjectKey();
                            }
                            if ( objectKey != null ) {
                                if ( objectKey instanceof POAKey ){
                                    key = ( POAKey )objectKey;
                                } else {
                                    key = POAKey.createKey( objectKey.getMarshaledKey() );    
                                }
                            } else {
                                throw new INTERNAL();
                            }
                        } else {
                            key = null;
                        }//reference = null
                    }//generic url, delegate in communication layers
                // URL resolution: object key = service url
                // it must throw a ForwardRequest
                if ( key != null ){
                	return key;
				} else {
	                throw new ForwardRequest(resolveGenericURL(urlKey)); 
	            }
                
            } else { //unable to get url from objectKey
                throw new INV_OBJREF( "Invalid Object Key" );
            }            
        } else {
            throw new INV_OBJREF( "ObjectKey cannot be null" );
        }
    }//resolvePOAKey  
            

    /**
     * Generic URL resolution
     * @throws ForwardRequest
     */
    protected org.omg.CORBA.Object resolveGenericURL(String urlKey)    	
    {   
        POAKey key;
        
        
        String initialReference = null;
        try {
            initialReference = 
                this.communicationManager.getInitialReference( 
                     urlKey 
                );
        } catch ( CommunicationException ce ){                        	
            throw new OBJECT_NOT_EXIST("Invalid object key: " + urlKey);
        }//initialReference retrieval                            
            
        
        org.omg.CORBA.Object reference = null;
        
        try {
            reference = resolve_initial_references( 
                initialReference
            );
        } catch ( InvalidName th ){
            StringBuffer msg = new StringBuffer();
            msg.append("Unable to resolve initial reference reference: ");
            msg.append(initialReference);
            msg.append(" from ");
            msg.append(urlKey);
            throw new OBJECT_NOT_EXIST(msg.toString());        
        }
            
         return reference;            
    }
    
   
    
    public String toString()
    {
        if (m_orb_name == null) {

            java.util.Date date;

            date = new java.util.Date(System.currentTimeMillis());

            StringBuffer name = new StringBuffer();
            name.append("TIDorbj ");
            name.append(st_version);
            name.append(" (id=\"");
            name.append(m_conf.orb_id);
            name.append("\") [");

            name.append( this.communicationManager.toString() );

            name.append("] created at ");
            name.append(date.toString());

            m_orb_name = name.toString();
        }

        return m_orb_name;

    }
    
    public void unregister_value_factory(String id)
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);
        synchronized (m_value_factories) {
            if (m_value_factories.remove(id) == null)
                throw new BAD_PARAM();
        }
    }

    /**
     * Operation called by threads in the ORB that are listening for the ORB
     * shutdown
     * 
     * @return true if the ORB is shutdowning, of false if the timeout is over
     */

    public boolean waitShutdown(long millis)
    {
        return m_state.waitForShutdown(millis);
    }

    public boolean work_pending()
    {
        if (m_destroyed)
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);

        if (m_state.isShutdowned())
            throw new BAD_INV_ORDER(4, CompletionStatus.COMPLETED_NO);

        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    //PRE: str starts with corbaname:

    protected org.omg.CORBA.Object corbanameUrlToObject(String str)
    {
        try {
            return Corbaname.toObject(this, str);
        }
        catch (Exception e) {
            throw new BAD_PARAM(e.getMessage(), 0,
                                CompletionStatus.COMPLETED_NO);
        }
    }

    protected void doCompleteShutdown()
    {
        java.lang.Object[] mgrs = new java.lang.Object[m_POAManagers.size()];

        m_POAManagers.copyInto(mgrs);

        for (int i = 0; i < mgrs.length; i++) {
            org.omg.PortableServer.POAManager mgr = 
                (org.omg.PortableServer.POAManager) mgrs[i];
            try {
                mgr.deactivate(true /* etherealize objects */, 
                               true /* wait_for_completion */);
            }
            catch (Exception e) {}
        }

        try {
            if (m_root_POA != null)
                m_root_POA.destroy(false, true);
        }
        catch (Exception e) {}

        try {
            if ( this.communicationManager != null ){
                this.communicationManager.shutdown( true );
            }
        }
        catch (Exception e) {}

        m_state.shutdowned();
    }

    // PRE str starts with file:

    protected org.omg.CORBA.Object fileUrlToObject(String str)
    {
        String file_str = str.substring(7);
        String file_url = null;

        if (file_str.length() == 0)
            throw new BAD_PARAM(file_str);
        try {
            java.io.FileReader file_r = new java.io.FileReader(file_str);
            java.io.BufferedReader url_r = new java.io.BufferedReader(file_r);

            file_url = url_r.readLine();
            url_r.close();

        } catch (java.io.FileNotFoundException fnf) {
            throw new BAD_PARAM("Invalid file url");
        } catch (SecurityException se) {
            throw new NO_PERMISSION(
                se.getMessage(), 0, CompletionStatus.COMPLETED_NO
            );
        } catch (Throwable th) {
            throw new UNKNOWN(
                th.toString(), 0, CompletionStatus.COMPLETED_NO
            );
        }

        if ( file_url != null && !file_url.startsWith("file:") ) {
            return string_to_object( file_url );
        } else {
            throw new BAD_PARAM( "No file url allowed in a file" );
        }
    }

    /**
     * Destructor.
     */

    protected void finalize()
    {
        if (!m_destroyed)
            destroy();
    }

    // PRE str starts with corbaloc:iiop: or corbaloc::

    //TODO: this should come from the communications layers
    protected org.omg.CORBA.Object iiopUrlToObject(String str)
    {
        IOR ior;
        ObjectImpl object = null;
        CommunicationDelegate delegate = null;

        try {
            ior = IIOPCorbaloc.getIOR(str);
        }
        catch (org.omg.CORBA.ORBPackage.InvalidName e) {
            throw new BAD_PARAM(e.getMessage(), 0,
                                CompletionStatus.COMPLETED_NO);
        }

        try {
            //TODO: should the communicationsManager (or the related layer) store
            //a cache of delegates for each IOR?
        	delegate = this.communicationManager.createDelegate( ior );
        } catch ( CommunicationException e ){
        	throw new BAD_PARAM( e.getMessage(), 0, CompletionStatus.COMPLETED_NO );
        }
        
        object = new ObjectImpl();
        object._set_delegate(delegate);
        return object;
    }

    protected es.tid.TIDorbj.core.CodecFactoryImpl initCodecFactory()
    {
        synchronized (this) {
            if (m_codec_factory == null)
                m_codec_factory = new CodecFactoryImpl(this);
        }
        return m_codec_factory;
    }

    protected es.tid.TIDorbj.dynAny.DynAnyFactoryImpl initDynAnyFactory()
    {
        synchronized (this) {
            if (m_dyn_factory == null) {
                try {
                    m_dyn_factory = 
                        new es.tid.TIDorbj.dynAny.DynAnyFactoryImpl();
                    m_dyn_factory.set_orb(this);
                }
                catch (Throwable th) {
                    throw new INITIALIZE("Cannot Initialize DynAnyFactory", 0,
                                         CompletionStatus.COMPLETED_NO);
                }
            }
        }
        return m_dyn_factory;
    }

    /**
     * ORB initialization. This method will be invoked by
     * <code>set_parametres()</code>.
     */

    protected void initOrb()
    {
        try {
            /**
             * TODO: remove CommLayer usage and migrate to CommunicationManager
             */
            this.communicationManager = CommunicationManager.getInstance( this );

            m_POAManagers = new java.util.Vector();

            m_value_factories = new java.util.Hashtable();

            m_orb_services = new ORBServices(this);

            m_thread_policy_context_manager = 
                new es.tid.TIDorbj.core.policy.PolicyContextManager(this);

            setupInitialReferences();

            if (m_conf.typecode_cache_size > 0)
                m_typecode_cache = 
                    new es.tid.TIDorbj.core.typecode.TypeCodeCache(
                            m_conf.typecode_cache_size);

            if (m_conf.trace_level != Trace.NONE) {
                m_trace = getTrace();
                             

                m_trace.print(Trace.ERROR, "ORB initialization OK!");

                m_conf.dump(m_trace.getLog());
                m_trace.flush();
            }

        }
        catch (Throwable e) {
            throw new INITIALIZE(e.toString(), 0, 
                                 CompletionStatus.COMPLETED_NO);
        }

    }
    
    public synchronized Trace getTrace()
        throws IOException
    {
        if (m_trace == null) {
            if (m_conf.trace_file != null) {
                if (m_conf.trace_num_files > 1) {

                    CircularTraceFile ctf = 
                        new CircularTraceFile(m_conf.trace_num_files,
                                              m_conf.trace_file_size,
                                              m_conf.trace_file);
                    m_trace = Trace.createTrace(ctf, toString(),
                                                m_conf.trace_level);
                } else {
                    m_trace = Trace.createTrace(m_conf.trace_file, toString(),
                                                m_conf.trace_level);
                }

            } else {
                m_trace = Trace.createTrace(toString(), m_conf.trace_level);
            }
        }
        
        return m_trace;
    }

    protected es.tid.TIDorbj.core.policy.PolicyCurrentImpl initPolicyCurrent()
    {
        synchronized (this) {
            if (m_policy_current == null)
                m_policy_current = 
                    new es.tid.TIDorbj.core.policy.PolicyCurrentImpl
                           (m_thread_policy_context_manager);
        }
        return m_policy_current;
    }

    protected es.tid.TIDorbj.core.compression.CompressionManagerImpl initCompressionManager()
    {
        synchronized (this) {
            if (m_compression_manager == null)
                m_compression_manager = 
                    new es.tid.TIDorbj.core.compression.CompressionManagerImpl
                           (this);
        }
        return m_compression_manager;
    }

    protected es.tid.TIDorbj.core.messaging.AMIManager initAMIManager()
    {
        synchronized (this) {
            if (m_ami_manager == null)
                m_ami_manager = 
                    new es.tid.TIDorbj.core.messaging.AMIManager(this);
        }
        return m_ami_manager;
    }

    protected es.tid.TIDorbj.core.policy.PolicyManagerImpl initPolicyManager()
    {
        synchronized (this) {
            if (m_orb_policy_manager == null)
                m_orb_policy_manager = 
                    new es.tid.TIDorbj.core.policy.PolicyManagerImpl(this);
        }
        return m_orb_policy_manager;
    }
            
    //PRE: str starts with corbaloc:rir:

    protected org.omg.CORBA.Object rirUrlToObject(String str)
    {
        if (!str.startsWith("corbaloc:rir:/"))
            throw new BAD_PARAM(str);
        try {
            return resolve_initial_references(str.substring(14));
        }
        catch (org.omg.CORBA.ORBPackage.InvalidName e) {
            throw new BAD_PARAM(e.getMessage(), 0,
                                CompletionStatus.COMPLETED_NO);
        }
    }

    protected void set_parameters(java.applet.Applet app,
                                  java.util.Properties props)
    {
        // read params
        m_conf = new ConfORB();
        m_conf.init(app, props);
        initOrb();
    }

    // CORBA ORB standard methods

    protected void set_parameters(String[] args, java.util.Properties props)
    {
        // read params
        m_conf = new ConfORB();
        m_conf.init(args, props);
        initOrb();
    }

    protected void setupInitialReferences()
    {
        int references = m_conf.initial_references.size();

        InitialReference ref = null;

        for (int i = 0; i < references; i++) {
            ref = (InitialReference) m_conf.initial_references.elementAt(i);
            m_orb_services.setService(ref.getName(),
                                      string_to_object(ref.getURL()));
        }

    }

    //PRE: str starts with corbaloc:tidorbj:
    protected org.omg.CORBA.Object tidorbjUrlToObject(String str)
    {
        IOR ior;
        ObjectImpl object = null;
        Delegate delegate = null;

        try {
            ior = Corbaloc.getIOR(str);
        }
        catch (org.omg.CORBA.ORBPackage.InvalidName e) {
            throw new BAD_PARAM(e.getMessage(), 0,
                                CompletionStatus.COMPLETED_NO);
        }

        try {
        	delegate = this.communicationManager.createDelegate( ior );
        } catch ( CommunicationException e ){
        	throw new BAD_PARAM( e.getMessage(), 0, CompletionStatus.COMPLETED_NO );
        }

        object = new ObjectImpl();
        object._set_delegate(delegate);
        return object;
    }
    
    public es.tid.TIDorbj.core.messaging.AMIManager getAMIManager() {
        return initAMIManager();
    }

    public void setORBservice(String object_name,
                              org.omg.CORBA.Object obj) {
        m_orb_services.setService(object_name, obj);
    }

}
