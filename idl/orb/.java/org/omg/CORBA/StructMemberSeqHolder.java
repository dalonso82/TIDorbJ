//
// StructMemberSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class StructMemberSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.StructMember[] value; 
  public StructMemberSeqHolder() {
    value = new org.omg.CORBA.StructMember[0];
  }

  public StructMemberSeqHolder(org.omg.CORBA.StructMember[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.StructMemberSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.StructMemberSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.StructMemberSeqHelper.type();
  };

}
