//
// FactoryAlreadyRegisteredHelper.java (helper)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

abstract public class FactoryAlreadyRegisteredHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, FactoryAlreadyRegistered value) {
    any.insert_Streamable(new FactoryAlreadyRegisteredHolder(value));
  };

  public static FactoryAlreadyRegistered extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof FactoryAlreadyRegisteredHolder){
          return ((FactoryAlreadyRegisteredHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[0];
      _type = _orb().create_exception_tc(id(), "FactoryAlreadyRegistered", members);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/Compression/FactoryAlreadyRegistered:1.0";
  };

  public static FactoryAlreadyRegistered read(org.omg.CORBA.portable.InputStream is) {
    if (! is.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Invalid repository id.");
    };
    FactoryAlreadyRegistered result = new FactoryAlreadyRegistered();
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, FactoryAlreadyRegistered val) {
    os.write_string(id());
  };

}
