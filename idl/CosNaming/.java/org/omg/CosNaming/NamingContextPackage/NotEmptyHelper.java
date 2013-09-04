//
// NotEmptyHelper.java (helper)
//
// File generated: Thu May 19 07:31:39 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CosNaming.NamingContextPackage;

abstract public class NotEmptyHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, NotEmpty value) {
    any.insert_Streamable(new NotEmptyHolder(value));
  };

  public static NotEmpty extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof NotEmptyHolder){
          return ((NotEmptyHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[0];
      _type = _orb().create_exception_tc(id(), "NotEmpty", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CosNaming/NamingContext/NotEmpty:1.0";
  };

  public static NotEmpty read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    NotEmpty result = new NotEmpty();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, NotEmpty val) {
    os.write_string(id());
  };

}
