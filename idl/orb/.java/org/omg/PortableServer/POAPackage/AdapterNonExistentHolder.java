//
// AdapterNonExistentHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAPackage;

final public class AdapterNonExistentHolder
   implements org.omg.CORBA.portable.Streamable {

  public AdapterNonExistent value; 
  public AdapterNonExistentHolder() {
  }

  public AdapterNonExistentHolder(AdapterNonExistent initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.PortableServer.POAPackage.AdapterNonExistentHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.PortableServer.POAPackage.AdapterNonExistentHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.PortableServer.POAPackage.AdapterNonExistentHelper.type();
  };

}
