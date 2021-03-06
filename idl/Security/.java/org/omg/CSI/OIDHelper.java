//
// OIDHelper.java (helper)
//
// File generated: Thu May 19 07:31:44 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CSI;

abstract public class OIDHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  public static void insert(org.omg.CORBA.Any any, byte[] value) {
    any.insert_Streamable(new OIDHolder(value));
  };

  public static byte[] extract(org.omg.CORBA.Any any) {
    if(any instanceof es.tid.CORBA.Any) {
      try {
        org.omg.CORBA.portable.Streamable holder =
          ((es.tid.CORBA.Any)any).extract_Streamable();
        if(holder instanceof OIDHolder){
          return ((OIDHolder) holder).value;
        }
      } catch (Exception e) {}
    }

    return read(any.create_input_stream());
  };

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      org.omg.CORBA.TypeCode original_type = _orb().create_sequence_tc(0 , org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet));
      _type = _orb().create_alias_tc(id(), "OID", original_type);
    }
    return _type;
  };

  public static String id() {
    return "IDL:omg.org/CSI/OID:1.0";
  };

  public static byte[] read(org.omg.CORBA.portable.InputStream is) {
    byte[] result;
      int length0 = is.read_ulong();
      result = new byte[length0];
      for (int i0=0; i0<length0; i0++) {
        result[i0] = is.read_octet();
      }
    return result;
  };

  public static void write(org.omg.CORBA.portable.OutputStream os, byte[] val) {
      os.write_ulong(val.length);
      for (int i0=0; i0<val.length; i0++) {
        os.write_octet(val[i0]);
      }
  };

}
