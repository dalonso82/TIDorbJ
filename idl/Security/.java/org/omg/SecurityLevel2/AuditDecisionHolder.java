//
// AuditDecisionHolder.java (holder)
//
// File generated: Thu May 19 07:31:45 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.SecurityLevel2;

final public class AuditDecisionHolder
   implements org.omg.CORBA.portable.Streamable {

  public AuditDecision value; 
  public AuditDecisionHolder() {
  }

  public AuditDecisionHolder(AuditDecision initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.SecurityLevel2.AuditDecisionHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.SecurityLevel2.AuditDecisionHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.SecurityLevel2.AuditDecisionHelper.type();
  };

}
