//
// CompressorIdLevelListHolder.java (holder)
//
// File generated: Thu May 19 07:31:43 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Compression;

final public class CompressorIdLevelListHolder
   implements org.omg.CORBA.portable.Streamable {

  public org.omg.Compression.CompressorIdLevel[] value; 
  public CompressorIdLevelListHolder() {
    value = new org.omg.Compression.CompressorIdLevel[0];
  }

  public CompressorIdLevelListHolder(org.omg.Compression.CompressorIdLevel[] initial) {
    value = initial;
  }

  public void _read(org.omg.CORBA.portable.InputStream is) {
    value = org.omg.Compression.CompressorIdLevelListHelper.read(is);
  };

  public void _write(org.omg.CORBA.portable.OutputStream os) {
    org.omg.Compression.CompressorIdLevelListHelper.write(os, value);
  };

  public org.omg.CORBA.TypeCode _type() {
    return org.omg.Compression.CompressorIdLevelListHelper.type();
  };

}
