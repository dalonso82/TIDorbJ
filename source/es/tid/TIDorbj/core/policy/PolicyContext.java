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
import org.omg.BiDirPolicy.BidirectionalPolicy;
import org.omg.BiDirPolicy.BidirectionalPolicyHelper;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INV_POLICY;
import org.omg.CORBA.InvalidPolicies;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.CORBA.SetOverrideType;
import org.omg.Messaging.QUEUE_ORDER_POLICY_TYPE;
import org.omg.Messaging.QueueOrderPolicy;
import org.omg.Messaging.QueueOrderPolicyHelper;
import org.omg.Messaging.ORDER_ANY;
import org.omg.Messaging.ORDER_TEMPORAL;
import org.omg.Messaging.ORDER_PRIORITY;
import org.omg.Messaging.ORDER_DEADLINE;
import org.omg.Messaging.REBIND_POLICY_TYPE;
import org.omg.Messaging.RELATIVE_REQ_TIMEOUT_POLICY_TYPE;
import org.omg.Messaging.RELATIVE_RT_TIMEOUT_POLICY_TYPE;
import org.omg.Messaging.REQUEST_END_TIME_POLICY_TYPE;
import org.omg.Messaging.REQUEST_PRIORITY_POLICY_TYPE;
import org.omg.Messaging.REQUEST_START_TIME_POLICY_TYPE;
import org.omg.Messaging.RebindModeHelper;
import org.omg.Messaging.RebindPolicy;
import org.omg.Messaging.RebindPolicyHelper;
import org.omg.Messaging.TRANSPARENT;
import org.omg.Messaging.NO_REBIND;
import org.omg.Messaging.NO_RECONNECT;
import org.omg.Messaging.RelativeRequestTimeoutPolicy;
import org.omg.Messaging.RelativeRequestTimeoutPolicyHelper;
import org.omg.Messaging.RelativeRoundtripTimeoutPolicy;
import org.omg.Messaging.RelativeRoundtripTimeoutPolicyHelper;
import org.omg.Messaging.RequestEndTimePolicy;
import org.omg.Messaging.RequestEndTimePolicyHelper;
import org.omg.Messaging.RequestPriorityPolicy;
import org.omg.Messaging.RequestPriorityPolicyHelper;
import org.omg.Messaging.RequestStartTimePolicy;
import org.omg.Messaging.RequestStartTimePolicyHelper;
import org.omg.ZIOP.COMPRESSION_ENABLING_POLICY_ID;
import org.omg.ZIOP.COMPRESSION_LOW_VALUE_POLICY_ID;
import org.omg.ZIOP.COMPRESSION_MIN_RATIO_POLICY_ID;
import org.omg.ZIOP.COMPRESSOR_ID_LEVEL_LIST_POLICY_ID;
import org.omg.ZIOP.CompressionEnablingPolicy;
import org.omg.ZIOP.CompressionLowValuePolicy;
import org.omg.ZIOP.CompressionMinRatioPolicy;
import org.omg.ZIOP.CompressorIdLevelListPolicy;
import org.omg.ZIOP.CompressionEnablingPolicyHelper;
import org.omg.ZIOP.CompressionLowValuePolicyHelper;
import org.omg.ZIOP.CompressionMinRatioPolicyHelper;
import org.omg.ZIOP.CompressorIdLevelListPolicyHelper;
import org.omg.ZIOP.CompressionEnablingPolicyValueHelper;
import org.omg.ZIOP.CompressionLowValuePolicyValueHelper;
import org.omg.ZIOP.CompressionMinRatioPolicyValueHelper;
import org.omg.Compression.CompressorIdLevelListHelper;
import org.omg.Compression.CompressorIdLevel;
import es.tid.TIDorbj.core.BidirectionalPolicyImpl;
import es.tid.TIDorbj.core.cdr.CDRInputStream;
import es.tid.TIDorbj.core.cdr.CDROutputStream;
import es.tid.TIDorbj.core.comm.giop.InvocationPoliciesContext;
import es.tid.TIDorbj.core.messaging.QoS;
import es.tid.TIDorbj.core.messaging.QueueOrderPolicyImpl;
import es.tid.TIDorbj.core.messaging.RelativeRequestTimeoutPolicyImpl;
import es.tid.TIDorbj.core.messaging.RelativeRoundtripTimeoutPolicyImpl;
import es.tid.TIDorbj.core.messaging.RequestEndTimePolicyImpl;
import es.tid.TIDorbj.core.messaging.RequestPriorityPolicyImpl;
import es.tid.TIDorbj.core.messaging.RequestStartTimePolicyImpl;
import es.tid.TIDorbj.util.UTC;
import es.tid.TIDorbj.core.ziop.CompressionEnablingPolicyImpl;
import es.tid.TIDorbj.core.ziop.CompressionLowValuePolicyImpl;
import es.tid.TIDorbj.core.ziop.CompressionMinRatioPolicyImpl;
import es.tid.TIDorbj.core.ziop.CompressorIdLevelListPolicyImpl;


public class PolicyContext

{   
    /**
     * QoS Policy
     */
    BidirectionalPolicy            bidirectionalPolicy = null;
    RebindPolicy                   rebindPolicy = null;
    RelativeRequestTimeoutPolicy   relativeRequestTimeoutPolicy = null;
    RelativeRoundtripTimeoutPolicy relativeRoundtripTimeoutPolicy = null;
    RequestPriorityPolicy          requestPriorityPolicy = null;
    RequestStartTimePolicy         requestStartTimePolicy = null;
    RequestEndTimePolicy           requestEndTimePolicy = null;
    QueueOrderPolicy               queueOrderPolicy = null;
    CompressionEnablingPolicy      compressionEnablingPolicy = null;
    CompressionLowValuePolicy      compressionLowValuePolicy = null;
    CompressionMinRatioPolicy      compressionMinRatioPolicy = null;
    CompressorIdLevelListPolicy    compressorIdLevelListPolicy = null;
    
    private PolicyContext m_father_context;
    
    private int numPolicies;
    
    
    /**
     * Constructor. Context may be structured in an hierachy for
     * the ORB, reference, request or thread
     * 
     * @param father_context father context (it may be null)
     */
    public PolicyContext(PolicyContext father_context)
    {
        m_father_context = father_context;        
        bidirectionalPolicy = null;  
        numPolicies = 0;
        
    }
    
    public void setFatherContext(PolicyContext father_context)
    {
        m_father_context = father_context;
    }
        
    private void clear()
    {
        bidirectionalPolicy = null; 
        rebindPolicy = null;
        relativeRequestTimeoutPolicy = null;
        relativeRoundtripTimeoutPolicy = null;
        requestPriorityPolicy = null;
        requestStartTimePolicy = null;
        requestEndTimePolicy = null;
        queueOrderPolicy = null;
        compressionEnablingPolicy = null;
        compressionLowValuePolicy = null;
        compressionMinRatioPolicy = null;
        compressorIdLevelListPolicy = null;
    }    
    

    public synchronized void setPolicies(Policy[] policies,
                                         SetOverrideType set_add)
        throws org.omg.CORBA.InvalidPolicies
    {

        if (set_add == SetOverrideType.SET_OVERRIDE) {
            clear();
        }

        java.util.Vector errors = null;

        for (short i = 0; i < policies.length; i++) {
            try {
                setPolicy(policies[i]);
            }
            catch (PolicyError pe) {
                if (errors == null)
                    errors = new java.util.Vector();
                errors.add(new Short(i));
            }
        }

        if (errors != null) {
            InvalidPolicies ex = new InvalidPolicies();

            int size = errors.size();

            ex.indices = new short[size];

            for (int i = 0; i < size; i++)
                ex.indices[i] = ((Short) errors.elementAt(i)).shortValue();

            throw ex;
        }

    }

    public Policy getPolicy(int type)
    {
       Policy policy = null;       
           
       switch(type) { 
           
       case RELATIVE_REQ_TIMEOUT_POLICY_TYPE.value:
            policy = getRelativeRequestTimeoutPolicy();
       		break;
       case RELATIVE_RT_TIMEOUT_POLICY_TYPE.value:
            policy = getRelativeRoundtripTimeoutPolicy();
        	break;
       case REBIND_POLICY_TYPE.value:
            policy =  getRebindPolicy();
        	break;
       case REQUEST_PRIORITY_POLICY_TYPE.value:
            policy = getRequestPriorityPolicy();
        	break;
       case REQUEST_START_TIME_POLICY_TYPE.value:
            policy = getRequestStartTimePolicy();
        	break;
       case REQUEST_END_TIME_POLICY_TYPE.value:
            policy = getRequestEndTimePolicy();
        	break;
       case QUEUE_ORDER_POLICY_TYPE.value:
            policy = getQueueOrderPolicy();
        	break;
       case BIDIRECTIONAL_POLICY_TYPE.value:
            policy = getBidirectionalPolicy();
        	break;        	
       case COMPRESSION_ENABLING_POLICY_ID.value:
            policy = getCompressionEnablingPolicy();
        	break;        	
       case COMPRESSION_LOW_VALUE_POLICY_ID.value:
            policy = getCompressionLowValuePolicy();
        	break;        	
       case COMPRESSION_MIN_RATIO_POLICY_ID.value:
            policy = getCompressionMinRatioPolicy();
        	break;        	
       case COMPRESSOR_ID_LEVEL_LIST_POLICY_ID.value:
            policy = getCompressorIdLevelListPolicy();
        	break;        	
       }
       
       if (policy == null) {
           throw new INV_POLICY();
       } else {
           return policy;
       }
       
           
       

    }

    public Policy[] getPolicies(int[] ts)
    {
        java.util.Vector list = new java.util.Vector();

        Policy policy = null;

        for (int i = 0; i < ts.length; i++) {
            policy = getPolicy(ts[i]);
            if (policy != null)
                list.add(policy);
        }

        Policy[] result = new Policy[list.size()];

        list.copyInto(result);

        return result;

    }

    public void setPolicy(Policy policy)
        throws PolicyError
    {

        if (policy == null)
            throw new BAD_PARAM("Null reference", 0,
                                CompletionStatus.COMPLETED_NO);

        int type = policy.policy_type();
        
        switch (type) {        
        case BIDIRECTIONAL_POLICY_TYPE.value:                
            bidirectionalPolicy = BidirectionalPolicyHelper.narrow(policy);
            break;           
        case RELATIVE_REQ_TIMEOUT_POLICY_TYPE.value:
            this.setRelativeRequestTimeoutPolicy(RelativeRequestTimeoutPolicyHelper.narrow(policy));
            break;
        case RELATIVE_RT_TIMEOUT_POLICY_TYPE.value:
            this.setRelativeRoundtripTimeoutPolicy(RelativeRoundtripTimeoutPolicyHelper.narrow(policy));
            break;
        case REBIND_POLICY_TYPE.value:
            this.setRebindPolicy(RebindPolicyHelper.narrow(policy));
            break;
        case REQUEST_PRIORITY_POLICY_TYPE.value:
            this.setRequestPriorityPolicy(RequestPriorityPolicyHelper.narrow(policy));
            break;
        case REQUEST_START_TIME_POLICY_TYPE.value:
            this.setRequestStartTimePolicy(RequestStartTimePolicyHelper.narrow(policy));
            break;
        case REQUEST_END_TIME_POLICY_TYPE.value:
            this.setRequestEndTimePolicy(RequestEndTimePolicyHelper.narrow(policy));
            break;
        case QUEUE_ORDER_POLICY_TYPE.value:
            this.setQueueOrderPolicy(QueueOrderPolicyHelper.narrow(policy));
            break;
        case COMPRESSION_ENABLING_POLICY_ID.value:
            this.setCompressionEnablingPolicy(CompressionEnablingPolicyHelper.narrow(policy));
            break;
        case COMPRESSION_LOW_VALUE_POLICY_ID.value:
            this.setCompressionLowValuePolicy(CompressionLowValuePolicyHelper.narrow(policy));
            break;
        case COMPRESSION_MIN_RATIO_POLICY_ID.value:
            this.setCompressionMinRatioPolicy(CompressionMinRatioPolicyHelper.narrow(policy));
            break;
        case COMPRESSOR_ID_LEVEL_LIST_POLICY_ID.value:
            this.setCompressorIdLevelListPolicy(CompressorIdLevelListPolicyHelper.narrow(policy));
            break;
        default:  
            throw new PolicyError(org.omg.CORBA.UNSUPPORTED_POLICY.value);
        }

    }
    

    /**
     * Duplicates completely the PolicyContext. It will be used by Reference
     * duplication.
     * @return a new PolicyContext
     */
    public synchronized PolicyContext duplicate()
    {         
        PolicyContext copy = new PolicyContext(m_father_context);
        
        copy.bidirectionalPolicy = bidirectionalPolicy;
        copy.rebindPolicy = rebindPolicy;
        copy.relativeRequestTimeoutPolicy = relativeRequestTimeoutPolicy;
        copy.relativeRoundtripTimeoutPolicy = relativeRoundtripTimeoutPolicy;
        copy.requestPriorityPolicy = requestPriorityPolicy;
        copy.requestStartTimePolicy = requestStartTimePolicy;
        copy.requestEndTimePolicy = requestEndTimePolicy;
        copy.queueOrderPolicy = queueOrderPolicy;
        copy.compressionEnablingPolicy = compressionEnablingPolicy;
        copy.compressionLowValuePolicy = compressionLowValuePolicy;
        copy.compressionMinRatioPolicy = compressionMinRatioPolicy;
        copy.compressorIdLevelListPolicy = compressorIdLevelListPolicy;
    
        copy.numPolicies = numPolicies;

        return copy;
    }     
  
    
    /**
     * @return Returns the rebindPolicy.
     */
    public RebindPolicy getRebindPolicy()
    {        
        if(rebindPolicy != null) {
                return rebindPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getRebindPolicy();
        } else {
            return null;
        }
    }
    
    
    /**
     * @param rebindPolicy The rebindPolicy to set.
     */
    public void setRebindPolicy(RebindPolicy rebindPolicy)
    {        
        if(this.rebindPolicy == null) {
            numPolicies++;
        }        
        if(rebindPolicy == null) {
            numPolicies--;
        }
        
        this.rebindPolicy = rebindPolicy;        
    }
    
    /**
     * @return Returns the relativeRequestTimeoutPolicy.
     */
    public RelativeRequestTimeoutPolicy getRelativeRequestTimeoutPolicy()
    {
        if(relativeRequestTimeoutPolicy != null) {
            return relativeRequestTimeoutPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getRelativeRequestTimeoutPolicy();
        } else {
            return null;
        }
    }
    
    /**
     * @param relativeRequestTimeoutPolicy The relativeRequestTimeoutPolicy to set.
     */
    public void 
    	setRelativeRequestTimeoutPolicy(RelativeRequestTimeoutPolicy relativeRequestTimeoutPolicy)
    {
        if(this.relativeRequestTimeoutPolicy == null) {
            numPolicies++;
        }        
        if(relativeRequestTimeoutPolicy == null) {
            numPolicies--;
        }
        
        this.relativeRequestTimeoutPolicy = relativeRequestTimeoutPolicy;
    }
    
    /**
     * @return Returns the relativeRountripTimeoutPolicy.
     */
    public RelativeRoundtripTimeoutPolicy getRelativeRoundtripTimeoutPolicy()
    {
        if(relativeRoundtripTimeoutPolicy != null) {
            return relativeRoundtripTimeoutPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getRelativeRoundtripTimeoutPolicy();
        } else {
            return null;
        }
    }
    
    /**
     * @param relativeRequestTimeoutPolicy The relativeRequestTimeoutPolicy to set.
     */
    public void 
    	setRelativeRoundtripTimeoutPolicy(RelativeRoundtripTimeoutPolicy relativeRoundtripTimeoutPolicy)
    {
        if(this.relativeRoundtripTimeoutPolicy == null) {
            numPolicies++;
        }        
        if(relativeRoundtripTimeoutPolicy == null) {
            numPolicies--;
        }
        
        this.relativeRoundtripTimeoutPolicy = relativeRoundtripTimeoutPolicy;
    }
       
    
    /**
     * @param requestEndTimePolicy The requestEndTimePolicy to set.
     */
    public void setRequestEndTimePolicy(RequestEndTimePolicy requestEndTimePolicy)
    {
        if(this.requestEndTimePolicy == null) {
            numPolicies++;
        }        
        if(requestEndTimePolicy == null) {
            numPolicies--;
        }
        
        this.requestEndTimePolicy = requestEndTimePolicy;
    }
    
    /**
     * @return Returns the requestEndTimePolicy.
     */
    public RequestEndTimePolicy getRequestEndTimePolicy()
    {
        if(requestEndTimePolicy != null) {
            return requestEndTimePolicy;
        } else if(m_father_context != null) {
            return m_father_context.getRequestEndTimePolicy();
        } else {
            return null;
        }
    }
    
    /**
     * @param requestPriorityPolicy The RequestPriorityPolicy to set.
     */
    public void setRequestPriorityPolicy(RequestPriorityPolicy requestPriorityPolicy)
    {
        if(this.requestPriorityPolicy == null) {
            numPolicies++;
        }        
        if(requestPriorityPolicy == null) {
            numPolicies--;
        }
        
        this.requestPriorityPolicy = requestPriorityPolicy;
    }
    
    /**
     * @return Returns the requestPriorityPolicy.
     */
    public RequestPriorityPolicy getRequestPriorityPolicy()
    {
        if(requestPriorityPolicy != null) {
            return requestPriorityPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getRequestPriorityPolicy();
        } else {
            return null;
        }
    }
    
    /**
     * @param requestStartTimePolicy The requestStartTimePolicy to set.
     */
    public void setRequestStartTimePolicy(
                                          RequestStartTimePolicy requestStartTimePolicy)
    {
        if(this.requestStartTimePolicy == null) {
            numPolicies++;
        }        
        if(requestStartTimePolicy == null) {
            numPolicies--;
        }
        this.requestStartTimePolicy = requestStartTimePolicy;
    }
    
    /**
     * @return Returns the requestStartTimePolicy.
     */
    public RequestStartTimePolicy getRequestStartTimePolicy()
    {
        if(requestStartTimePolicy != null) {
            return requestStartTimePolicy;
        } else if(m_father_context != null) {
            return m_father_context.getRequestStartTimePolicy();
        } else {
            return null;
        }
    }
    
    
    
       
    
    /**
     * @return Returns the queueOrderPolicy.
     */
    public QueueOrderPolicy getQueueOrderPolicy()
    {
        if(queueOrderPolicy != null) {
            return queueOrderPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getQueueOrderPolicy();
        } else {
            return null;
        }        
    }
    
    /**
     * @param rebindPolicy The queueOrderPolicy to set.
     */
    public void setQueueOrderPolicy(QueueOrderPolicy queueOrderPolicy)
    {        
        if(this.queueOrderPolicy == null) {
            numPolicies++;
        }        
        if(queueOrderPolicy == null) {
            numPolicies--;
        }
        
        this.queueOrderPolicy = queueOrderPolicy;        
    }
    
    
     
       
        
    /**
     * @param priorityModelPolicy The queueOrderPolicy to set.
     */
    public void setBidirectionalPolicy(BidirectionalPolicy bidirectionalPolicy)
    {        
        if(this.bidirectionalPolicy == null) {
            numPolicies++;
        }        
        if(bidirectionalPolicy == null) {
            numPolicies--;
        }
        
        this.bidirectionalPolicy = bidirectionalPolicy;        
    }
    
    /**
     * @return Returns the queueOrderPolicy.
     */
    public BidirectionalPolicy getBidirectionalPolicy()
    {
        if(bidirectionalPolicy != null) {
            return bidirectionalPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getBidirectionalPolicy();
        } else {
            return null;
        }        
    }
    
    /**
     * @param compressionEnablingPolicy The compressionEnablingPolicy to set.
     */
    public void setCompressionEnablingPolicy(CompressionEnablingPolicy compressionEnablingPolicy)
    {
        if(this.compressionEnablingPolicy == null) {
            numPolicies++;
        }        
        if(compressionEnablingPolicy == null) {
            numPolicies--;
        }
        
        this.compressionEnablingPolicy = compressionEnablingPolicy;
    }
    
    /**
     * @return Returns the compressionEnablingPolicy.
     */
    public CompressionEnablingPolicy getCompressionEnablingPolicy()
    {
        if(compressionEnablingPolicy != null) {
            return compressionEnablingPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getCompressionEnablingPolicy();
        } else {
            return null;
        }
    }

    /**
     * @param compressionLowValuePolicy The compressionLowValuePolicy to set.
     */
    public void setCompressionLowValuePolicy(CompressionLowValuePolicy compressionLowValuePolicy)
    {
        if(this.compressionLowValuePolicy == null) {
            numPolicies++;
        }        
        if(compressionLowValuePolicy == null) {
            numPolicies--;
        }
        
        this.compressionLowValuePolicy = compressionLowValuePolicy;
    }
    
    /**
     * @return Returns the compressionLowValuePolicy.
     */
    public CompressionLowValuePolicy getCompressionLowValuePolicy()
    {
        if(compressionLowValuePolicy != null) {
            return compressionLowValuePolicy;
        } else if(m_father_context != null) {
            return m_father_context.getCompressionLowValuePolicy();
        } else {
            return null;
        }
    }

    /**
     * @param compressionMinRatioPolicy The compressionMinRatioPolicy to set.
     */
    public void setCompressionMinRatioPolicy(CompressionMinRatioPolicy compressionMinRatioPolicy)
    {
        if(this.compressionMinRatioPolicy == null) {
            numPolicies++;
        }        
        if(compressionMinRatioPolicy == null) {
            numPolicies--;
        }
        
        this.compressionMinRatioPolicy = compressionMinRatioPolicy;
    }
    
    /**
     * @return Returns the compressionMinRatioPolicy.
     */
    public CompressionMinRatioPolicy getCompressionMinRatioPolicy()
    {
        if(compressionMinRatioPolicy != null) {
            return compressionMinRatioPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getCompressionMinRatioPolicy();
        } else {
            return null;
        }
    }

    /**
     * @param compressorIdLevelListPolicy The compressorIdLevelListPolicy to set.
     */
    public void setCompressorIdLevelListPolicy(CompressorIdLevelListPolicy compressorIdLevelListPolicy)
    {
        if(this.compressorIdLevelListPolicy == null) {
            numPolicies++;
        }        
        if(compressorIdLevelListPolicy == null) {
            numPolicies--;
        }
        
        this.compressorIdLevelListPolicy = compressorIdLevelListPolicy;
    }
    
    /**
     * @return Returns the compressorIdLevelListPolicy.
     */
    public CompressorIdLevelListPolicy getCompressorIdLevelListPolicy()
    {
        if(compressorIdLevelListPolicy != null) {
            return compressorIdLevelListPolicy;
        } else if(m_father_context != null) {
            return m_father_context.getCompressorIdLevelListPolicy();
        } else {
            return null;
        }
    }


    
    public InvocationPoliciesContext getInvocationPolicyServiceContext()
    {        
        RequestEndTimePolicy endPolicy = 
            QoS.getEffectiveRequestEndTimePolicy(this);
        
        RequestPriorityPolicy priorityPolicy = getRequestPriorityPolicy();
    
        if((endPolicy != null) || (priorityPolicy != null)){
            PolicyContext invocationPolicies = new PolicyContext(null);
            if(endPolicy != null) {
                invocationPolicies.setRequestEndTimePolicy(endPolicy);                
            }
            if(priorityPolicy != null) {
                invocationPolicies.setRequestPriorityPolicy(priorityPolicy);
            }
            return new InvocationPoliciesContext(invocationPolicies);
        } else {
            return null;
        }        
    }
    
      
    
    
    public synchronized void dump(java.io.PrintWriter writer)
    {
        if(bidirectionalPolicy != null) {
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(BidirectionalPolicyHelper.id());
            writer.print('=');
            short bidir_value = bidirectionalPolicy.value();
            if (bidir_value == org.omg.BiDirPolicy.NORMAL.value) {
                writer.print("NORMAL");
            } else {
                writer.print("BOTH");
            }
        }
        
        if(relativeRoundtripTimeoutPolicy != null) {
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(RelativeRequestTimeoutPolicyHelper.id());
            writer.print('=');
            writer.print(UTC.toTimeInMillis(
                         relativeRoundtripTimeoutPolicy.relative_expiry()));
            writer.print(" ms.");
        } 

        if (rebindPolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(RebindPolicyHelper.id());
            writer.print('=');
            short rebind_value = rebindPolicy.rebind_mode();
            switch (rebind_value){
            case org.omg.Messaging.TRANSPARENT.value:
                writer.print("TRANSPARENT");
                break;
            case org.omg.Messaging.NO_REBIND.value:
                writer.print("NO_REBIND");
                break;
            case org.omg.Messaging.NO_RECONNECT.value: 
                writer.print("NO_RECONNECT");
                break;
            }
        }

        if (relativeRequestTimeoutPolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(RelativeRequestTimeoutPolicyHelper.id());
            writer.print('=');
            writer.print(UTC.toTimeInMillis(
                         relativeRequestTimeoutPolicy.relative_expiry()));
            writer.print(" ms.");
        }

        if (requestPriorityPolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(RequestPriorityPolicyHelper.id());
            writer.print('=');
            writer.print("[" + requestPriorityPolicy.priority_range().min + "," +
                           requestPriorityPolicy.priority_range().max + "]");
        }

        if (requestStartTimePolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(RequestStartTimePolicyHelper.id());
            writer.print('=');
            writer.print(UTC.toTimeInMillis(
                         requestStartTimePolicy.start_time().time));
            writer.print(" ms.");
        }

        if (requestEndTimePolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(RequestEndTimePolicyHelper.id());
            writer.print('=');
            writer.print(UTC.toTimeInMillis(
                         requestEndTimePolicy.end_time().time));
            writer.print(" ms.");
        }

        if (queueOrderPolicy != null) {
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(QueueOrderPolicyHelper.id());
            writer.print('=');
            short value = queueOrderPolicy.allowed_orders();
            switch (value) {
            case org.omg.Messaging.ORDER_ANY.value:
                writer.print("ORDER_ANY");
                break;
            case org.omg.Messaging.ORDER_TEMPORAL.value:
                writer.print("ORDER_TEMPORAL");
                break;
            case org.omg.Messaging.ORDER_PRIORITY.value:
                writer.print("ORDER_PRIORITY");
                break;
            case org.omg.Messaging.ORDER_DEADLINE.value:
                writer.print("ORDER_DEADLINE");
                break;
            }
        }


        if (compressionEnablingPolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(CompressionEnablingPolicyHelper.id());
            writer.print('=');
            writer.print(compressionEnablingPolicy.compression_enabled());
        }

        if (compressionLowValuePolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(CompressionLowValuePolicyHelper.id());
            writer.print('=');
            writer.print(compressionLowValuePolicy.low_value());
        }

        if (compressionMinRatioPolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(CompressionMinRatioPolicyHelper.id());
            writer.print('=');
            writer.print(compressionMinRatioPolicy.ratio());
        }

        if (compressorIdLevelListPolicy != null){
            writer.print('\n');
            writer.print('\t');
            writer.print('\t');
            writer.print(CompressorIdLevelListPolicyHelper.id());
            writer.print('=');
            CompressorIdLevel[] compressors = 
                compressorIdLevelListPolicy.compressor_ids();
            for (int i=0; i < compressors.length; i++) {
                writer.print("(" + compressors[i].compressor_id + "," + 
                             compressors[i].compression_level + ")" );
            }
        }
    }
    
    
    /**
     *  Reads the policies as a pair PolicyType/encapsuled policy data
     * @param input
     */
    public void partialRead(CDRInputStream input)
    {        
        int size = input.read_ulong();
        
        Policy policy = null;
        for (int i = 0; i< size; i++) {
            policy = PolicyFactory.readPolicy(input);
            if(policy != null) {            
                try {
                    setPolicy(policy);
                }
                catch (PolicyError e) {//unreachable
                }
            }
        }        
    }
      
    /**
     * Writes the policies as a pair PolicyType/encalsuled policy data
     * @param output the outputstream
     */
    public void write(CDROutputStream output)
    {        
        output.write_ulong(numPolicies);
        
        if(rebindPolicy != null) {            
            // write PolicyType
            output.write_ulong(rebindPolicy.policy_type());
            output.enterEncapsulation();   
            RebindModeHelper.write(output, rebindPolicy.rebind_mode());                    
            output.exitEncapsulation();       
        }           
                
            
        if(relativeRequestTimeoutPolicy != null) {
            // write PolicyType           
            output.write_ulong(relativeRequestTimeoutPolicy.policy_type());
            output.enterEncapsulation();   
            RelativeRequestTimeoutPolicyImpl.write(output,relativeRequestTimeoutPolicy);
            output.exitEncapsulation();
        }
        
        if(relativeRoundtripTimeoutPolicy != null) {    
            // write PolicyType
            output.write_ulong(relativeRoundtripTimeoutPolicy.policy_type());
            output.enterEncapsulation();   
            RelativeRoundtripTimeoutPolicyImpl.write(output, 
                                                     relativeRoundtripTimeoutPolicy);   
            output.exitEncapsulation();       
        }
        
        if(requestStartTimePolicy != null) {
            // write PolicyType
            output.write_ulong(requestStartTimePolicy.policy_type());
            output.enterEncapsulation(); 
            RequestStartTimePolicyImpl.write(output, requestStartTimePolicy); 
            output.exitEncapsulation();            
        }
        
        if(requestPriorityPolicy != null) {
            // write PolicyType
            output.write_ulong(requestPriorityPolicy.policy_type());
            output.enterEncapsulation(); 
            RequestPriorityPolicyImpl.write(output, requestPriorityPolicy); 
            output.exitEncapsulation();            
        }
        
        if(requestEndTimePolicy != null) {
            // write PolicyType
            output.write_ulong(requestEndTimePolicy.policy_type());
            // write Encapsulation
            output.enterEncapsulation();
            RequestEndTimePolicyImpl.write(output, requestEndTimePolicy);
            output.exitEncapsulation(); 
        } 
        
        if(queueOrderPolicy != null) {                      
            // write PolicyType
            output.write_ulong(queueOrderPolicy.policy_type());
            output.enterEncapsulation();
            QueueOrderPolicyImpl.write(output,queueOrderPolicy );
            output.exitEncapsulation();
        } 

        if(compressionEnablingPolicy != null) {            
            // write PolicyType
            output.write_ulong(compressionEnablingPolicy.policy_type());
            output.enterEncapsulation();   
            CompressionEnablingPolicyImpl.write(output, compressionEnablingPolicy);
            output.exitEncapsulation();       
        }

        if(compressionLowValuePolicy != null) {            
            // write PolicyType
            output.write_ulong(compressionLowValuePolicy.policy_type());
            output.enterEncapsulation();   
            CompressionLowValuePolicyImpl.write(output, compressionLowValuePolicy);
            output.exitEncapsulation();       
        }

        if(compressionMinRatioPolicy != null) {            
            // write PolicyType
            output.write_ulong(compressionMinRatioPolicy.policy_type());
            output.enterEncapsulation();   
            CompressionMinRatioPolicyImpl.write(output, compressionMinRatioPolicy);
            output.exitEncapsulation();       
        }

        if(compressorIdLevelListPolicy != null) {            
            // write PolicyType
            output.write_ulong(compressorIdLevelListPolicy.policy_type());
            output.enterEncapsulation();   
            CompressorIdLevelListPolicyImpl.write(output, compressorIdLevelListPolicy);
            output.exitEncapsulation();       
        }
                
        if(this.bidirectionalPolicy != null) {
            // write PolicyType
            output.write_ulong(bidirectionalPolicy.policy_type());
            output.enterEncapsulation();   
            BidirectionalPolicyImpl.write(output,bidirectionalPolicy );                
            output.exitEncapsulation();   
        }
    }

    /**
     * @return
     */
    public int getSize()
    {        
        return this.numPolicies;
    }
    
    
}
