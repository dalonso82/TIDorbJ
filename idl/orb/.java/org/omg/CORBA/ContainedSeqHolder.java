//
// ContainedSeqHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ContainedSeqHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.CORBA.Contained[] value; 
  public ContainedSeqHolder() {
    value = new org.omg.CORBA.Contained[0];
  }

  public ContainedSeqHolder(org.omg.CORBA.Contained[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ContainedSeqHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ContainedSeqHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ContainedSeqHelper.type();
  };

}
