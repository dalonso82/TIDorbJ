//
// DynUnionHolder.java (holder)
//
// File generated: Thu May 19 07:31:41 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.DynamicAny;

final public class DynUnionHolder
   implements org.omg.CORBA.portable.Streamable {

  public DynUnion value; 
  public DynUnionHolder() {
  }

  public DynUnionHolder(DynUnion initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.DynamicAny.DynUnionHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.DynamicAny.DynUnionHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.DynamicAny.DynUnionHelper.type();
  };

}
