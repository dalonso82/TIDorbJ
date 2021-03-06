/***** Copyright (c) 1999 Object Management Group. Unlimited rights to 
       duplicate and use this code are hereby granted provided that this 
       copyright notice is included.
*****/

package org.omg.CORBA;

public class UNKNOWN extends org.omg.CORBA.SystemException {

    public UNKNOWN() {
        super(null, 0, CompletionStatus.COMPLETED_NO);
    }

    public UNKNOWN(int minor, CompletionStatus completed) {
        super(null, minor, completed);
    }

    public UNKNOWN(String reason) {
        super(reason, 0, CompletionStatus.COMPLETED_NO);
    }

    public UNKNOWN(String reason, int minor, CompletionStatus completed) {
        super(reason, minor, completed);
    }

}
