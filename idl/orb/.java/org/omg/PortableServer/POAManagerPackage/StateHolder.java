//
// StateHolder.java (holder)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.PortableServer.POAManagerPackage;

final public class StateHolder
   implements org.omg.CORBA.portable.Streamable {

  public State value; 
  public StateHolder() {
  }

  public StateHolder(State initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.PortableServer.POAManagerPackage.StateHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.PortableServer.POAManagerPackage.StateHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.PortableServer.POAManagerPackage.StateHelper.type();
  };

}
