/*
* MORFEO Project
* http://www.morfeo-project.org
*
* Component: TIDorbJ
* Programming Language: Java
*
* File: $Source$
* Version: $Revision: 395 $
* Date: $Date: 2009-05-27 16:10:32 +0200 (Wed, 27 May 2009) $
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
package es.tid.TIDorbj.core.policy;

import org.omg.BiDirPolicy.BIDIRECTIONAL_POLICY_TYPE;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.Messaging.QUEUE_ORDER_POLICY_TYPE;
import org.omg.Messaging.REBIND_POLICY_TYPE;
import org.omg.Messaging.RELATIVE_REQ_TIMEOUT_POLICY_TYPE;
import org.omg.Messaging.RELATIVE_RT_TIMEOUT_POLICY_TYPE;
import org.omg.Messaging.REQUEST_END_TIME_POLICY_TYPE;
import org.omg.Messaging.REQUEST_PRIORITY_POLICY_TYPE;
import org.omg.Messaging.REQUEST_START_TIME_POLICY_TYPE;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;
import org.omg.PortableServer.ID_UNIQUENESS_POLICY_ID;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.omg.ZIOP.COMPRESSION_ENABLING_POLICY_ID;
import org.omg.ZIOP.COMPRESSION_LOW_VALUE_POLICY_ID;
import org.omg.ZIOP.COMPRESSION_MIN_RATIO_POLICY_ID;
import org.omg.ZIOP.COMPRESSOR_ID_LEVEL_LIST_POLICY_ID;

import es.tid.TIDorbj.core.BidirectionalPolicyImpl;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.messaging.QueueOrderPolicyImpl;
import es.tid.TIDorbj.core.messaging.RebindPolicyImpl;
import es.tid.TIDorbj.core.messaging.RelativeRequestTimeoutPolicyImpl;
import es.tid.TIDorbj.core.messaging.RelativeRoundtripTimeoutPolicyImpl;
import es.tid.TIDorbj.core.messaging.RequestEndTimePolicyImpl;
import es.tid.TIDorbj.core.messaging.RequestStartTimePolicyImpl;
import es.tid.TIDorbj.core.poa.policies.IdAssignmentPolicyImpl;
import es.tid.TIDorbj.core.poa.policies.IdUniquenessPolicyImpl;
import es.tid.TIDorbj.core.poa.policies.LifespanPolicyImpl;
import es.tid.TIDorbj.core.poa.policies.RequestProcessingPolicyImpl;
import es.tid.TIDorbj.core.poa.policies.ServantRetentionPolicyImpl;
import es.tid.TIDorbj.core.poa.policies.ThreadPolicyImpl;
import es.tid.TIDorbj.core.messaging.RequestPriorityPolicyImpl;
import es.tid.TIDorbj.core.ziop.CompressionEnablingPolicyImpl;
import es.tid.TIDorbj.core.ziop.CompressionLowValuePolicyImpl;
import es.tid.TIDorbj.core.ziop.CompressionMinRatioPolicyImpl;
import es.tid.TIDorbj.core.ziop.CompressorIdLevelListPolicyImpl;

/**
 * Methods used to instantiate ORB policies.
 * 
 * @author Juan A. C&aacute;ceres
 * @version 1.0
 */

public class PolicyFactory
{

    
    /**
     * Attempts to instanciate a <B>CORBA </B> policy.
     * 
     * @param type
     *            the CORBA policy identifier
     * @param val
     *            the policy value inserted in a <code>Any</code>
     * @throws org.omg.CORBA.PolicyError
     */

    public static org.omg.CORBA.Policy createPolicy(int type, Any val)
        throws org.omg.CORBA.PolicyError
    {

        if (val == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        switch (type) {          
            case ID_UNIQUENESS_POLICY_ID.value:
                return IdUniquenessPolicyImpl.createPolicy(val);            
            case ID_ASSIGNMENT_POLICY_ID.value:
                return IdAssignmentPolicyImpl.createPolicy(val);
            case LIFESPAN_POLICY_ID.value:
                return LifespanPolicyImpl.createPolicy(val);   
            case REBIND_POLICY_TYPE.value:
                return RebindPolicyImpl.createPolicy(val);            
            case BIDIRECTIONAL_POLICY_TYPE.value:
                return BidirectionalPolicyImpl.createPolicy(val);
            case QUEUE_ORDER_POLICY_TYPE.value:
                return QueueOrderPolicyImpl.createPolicy(val);
            case RELATIVE_REQ_TIMEOUT_POLICY_TYPE.value:
                return RelativeRequestTimeoutPolicyImpl.createPolicy(val);
            case RELATIVE_RT_TIMEOUT_POLICY_TYPE.value:
                return RelativeRoundtripTimeoutPolicyImpl.createPolicy(val);
            case REQUEST_PROCESSING_POLICY_ID.value:
                return RequestProcessingPolicyImpl.createPolicy(val);
            case REQUEST_PRIORITY_POLICY_TYPE.value:
                return RequestPriorityPolicyImpl.createPolicy(val);
            case REQUEST_START_TIME_POLICY_TYPE.value:
                return RequestStartTimePolicyImpl.createPolicy(val);
            case REQUEST_END_TIME_POLICY_TYPE.value:
                return RequestEndTimePolicyImpl.createPolicy(val);
            case SERVANT_RETENTION_POLICY_ID.value:
                return ServantRetentionPolicyImpl.createPolicy(val);
            case THREAD_POLICY_ID.value:
                return ThreadPolicyImpl.createPolicy(val);
            case COMPRESSION_ENABLING_POLICY_ID.value:
                return CompressionEnablingPolicyImpl.createPolicy(val);
            case COMPRESSION_LOW_VALUE_POLICY_ID.value:
                return CompressionLowValuePolicyImpl.createPolicy(val);
            case COMPRESSION_MIN_RATIO_POLICY_ID.value:
                return CompressionMinRatioPolicyImpl.createPolicy(val);
            case COMPRESSOR_ID_LEVEL_LIST_POLICY_ID.value:
                return CompressorIdLevelListPolicyImpl.createPolicy(val);
            default:
                throw new PolicyError(org.omg.CORBA.UNSUPPORTED_POLICY.value);
        }

    }

    /**
     * 
     * Reads a policy from an INVOCATION_POLICY ServiceContext encapsulation.
     * See QoS section: "22.3 Propagation of Messaging QoS"
     * 
     * @param input
     * @return
     */
    public static Policy readPolicy(CDRInputStream input)
    {
        int policy_type = input.read_ulong();
        Policy policy = null;
             
        
        switch (policy_type) {
            case BIDIRECTIONAL_POLICY_TYPE.value:     
                input.enterEncapsulation();
                policy =  BidirectionalPolicyImpl.read(input);   
                input.exitEncapsulation();
            	break;            
            case REQUEST_END_TIME_POLICY_TYPE.value: 
                input.enterEncapsulation();                
                policy = RequestEndTimePolicyImpl.read(input);
                input.exitEncapsulation();
    			break;
            case REQUEST_START_TIME_POLICY_TYPE.value: 
                input.enterEncapsulation();                
                policy = RequestStartTimePolicyImpl.read(input);
                input.exitEncapsulation();
    			break;
            case RELATIVE_REQ_TIMEOUT_POLICY_TYPE.value: 
                input.enterEncapsulation();                
                policy = RelativeRequestTimeoutPolicyImpl.read(input);
                input.exitEncapsulation();
    			break;
            case REQUEST_PRIORITY_POLICY_TYPE.value: 
                input.enterEncapsulation();	
                policy = RequestPriorityPolicyImpl.read(input);                
                input.exitEncapsulation();
    			break; 
            case COMPRESSION_ENABLING_POLICY_ID.value: 
                input.enterEncapsulation();	
                policy = CompressionEnablingPolicyImpl.read(input);                
                input.exitEncapsulation();
    			break; 
            case COMPRESSOR_ID_LEVEL_LIST_POLICY_ID.value: 
                input.enterEncapsulation();	
                policy = CompressorIdLevelListPolicyImpl.read(input);                
                input.exitEncapsulation();
    			break; 
            default: // policy not supported skip               
               input.skipEncapsulation();
               return null;        
        }
        
        return policy;
    }
   
}
