//
// SyncScopePolicyHelper.java (helper)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

abstract public class SyncScopePolicyHelper {

  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }

  private static org.omg.CORBA.TypeCode _type = null;
  public static org.omg.CORBA.TypeCode type() {
    if (_type == null) {
      _type = _orb().create_interface_tc(id(), "SyncScopePolicy");
    }
    return _type;
  }

  public static String id() {
    return "IDL:omg.org/Messaging/SyncScopePolicy:1.0";
  };

  public static void insert(org.omg.CORBA.Any any, SyncScopePolicy value) {
    any.insert_Object((org.omg.CORBA.Object)value, type());
  };

  public static SyncScopePolicy extract(org.omg.CORBA.Any any) {
    org.omg.CORBA.Object obj = any.extract_Object();
    SyncScopePolicy value = narrow(obj);
    return value;
  };

  public static SyncScopePolicy read(org.omg.CORBA.portable.InputStream is) {
    return narrow(is.read_Object(), true); 
  }

  public static void write(org.omg.CORBA.portable.OutputStream os, SyncScopePolicy val) {
    if (!(os instanceof org.omg.CORBA_2_3.portable.OutputStream)) {;
      throw new org.omg.CORBA.BAD_PARAM();
    };
    if (val != null && !(val instanceof org.omg.CORBA.portable.ObjectImpl)) {;
      throw new org.omg.CORBA.BAD_PARAM();
    };
    os.write_Object((org.omg.CORBA.Object)val);
  }

  public static SyncScopePolicy narrow(org.omg.CORBA.Object obj) {
    return narrow(obj, false);
  }

  public static SyncScopePolicy unchecked_narrow(org.omg.CORBA.Object obj) {
    return narrow(obj, true);
  }

  private static SyncScopePolicy narrow(org.omg.CORBA.Object obj, boolean is_a) {
    if (obj == null) {
      return null;
    }
    if (obj instanceof SyncScopePolicy) {
      return (SyncScopePolicy)obj;
    }
    throw new org.omg.CORBA.BAD_PARAM();
  }

}
