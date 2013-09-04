//
// _PrimitiveDefStub.java (stub)
//
// File generated: Thu May 19 07:31:32 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.CORBA;

public class _PrimitiveDefStub
 extends org.omg.CORBA.portable.ObjectImpl
 implements PrimitiveDef {

  public java.lang.String[] _ids() {
    return __ids;
  }

  private static java.lang.String[] __ids = {
    "IDL:omg.org/CORBA/PrimitiveDef:1.0",
    "IDL:omg.org/CORBA/IDLType:1.0",
    "IDL:omg.org/CORBA/IRObject:1.0"  };

  public org.omg.CORBA.PrimitiveKind kind() {
    org.omg.CORBA.Request _request = this._request("_get_kind");
    _request.set_return_type(org.omg.CORBA.PrimitiveKindHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.PrimitiveKind _result;
    _result = org.omg.CORBA.PrimitiveKindHelper.extract(_request.return_value());
    return _result;  }

  public org.omg.CORBA.TypeCode type() {
    org.omg.CORBA.Request _request = this._request("_get_type");
    _request.set_return_type(org.omg.CORBA.TypeCodeHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.TypeCode _result;
    _result = org.omg.CORBA.TypeCodeHelper.extract(_request.return_value());
    return _result;  }

  public org.omg.CORBA.DefinitionKind def_kind() {
    org.omg.CORBA.Request _request = this._request("_get_def_kind");
    _request.set_return_type(org.omg.CORBA.DefinitionKindHelper.type());
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      throw (org.omg.CORBA.SystemException) _exception;
    };
    org.omg.CORBA.DefinitionKind _result;
    _result = org.omg.CORBA.DefinitionKindHelper.extract(_request.return_value());
    return _result;  }

  public void destroy() {
    org.omg.CORBA.Request _request = this._request("destroy");
    _request.invoke();
    java.lang.Exception _exception = _request.env().exception();
    if (_exception != null) {
      if (_exception instanceof org.omg.CORBA.UnknownUserException) {
        org.omg.CORBA.UnknownUserException _userException = 
          (org.omg.CORBA.UnknownUserException) _exception;
      }
      throw (org.omg.CORBA.SystemException) _exception;
    };
  }




}
