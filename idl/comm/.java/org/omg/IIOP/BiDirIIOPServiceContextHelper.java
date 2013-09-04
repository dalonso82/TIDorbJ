//
// BiDirIIOPServiceContextHelper.java (helper)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.IIOP;

abstract public class BiDirIIOPServiceContextHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, BiDirIIOPServiceContext value) {
    any.insert_Streamable(new BiDirIIOPServiceContextHolder(value));
  };

  public static BiDirIIOPServiceContext extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof BiDirIIOPServiceContextHolder){
          return ((BiDirIIOPServiceContextHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[1];
      members[0] = new org.omg.CORBA.StructMember("listen_points", org.omg.IIOP.ListenPointListHelper.type(), null);
      _type = _orb().create_struct_tc(id(), "BiDirIIOPServiceContext", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/IIOP/BiDirIIOPServiceContext:1.0";
  };

  public static BiDirIIOPServiceContext read(org.omg.CORBA.portable.InputStream is) {
    BiDirIIOPServiceContext result = new BiDirIIOPServiceContext();
    result.listen_points = org.omg.IIOP.ListenPointListHelper.read(is);
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, BiDirIIOPServiceContext val) {
    org.omg.IIOP.ListenPointListHelper.write(os,val.listen_points);
  };

}