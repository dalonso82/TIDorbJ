//
// Description.java (struct)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA.ContainerPackage;

public class Description
   implements org.omg.CORBA.portable.IDLEntity {

  public org.omg.CORBA.Contained contained_object;
  public org.omg.CORBA.DefinitionKind kind;
  public org.omg.CORBA.Any value;

  public Description() {
  }

  public Description(org.omg.CORBA.Contained contained_object, org.omg.CORBA.DefinitionKind kind, org.omg.CORBA.Any value) {
    this.contained_object = contained_object;
    this.kind = kind;
    this.value = value;
  }

}
