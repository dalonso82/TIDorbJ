//
// ValueDefHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

final public class ValueDefHolder
   implements org.omg.CORBA.portable.Streamable {

  public ValueDef value; 
  public ValueDefHolder() {
  }

  public ValueDefHolder(ValueDef initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.CORBA.ValueDefHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.CORBA.ValueDefHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.CORBA.ValueDefHelper.type();
  };

}
