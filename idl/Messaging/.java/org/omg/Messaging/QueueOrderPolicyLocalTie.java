//
// QueueOrderPolicyLocalTie.java (tie)
//
// File generated: Thu May 19 07:31:42 CEST 2011
//   by TIDorb idl2java 1.3.12
//

package org.omg.Messaging;

public class QueueOrderPolicyLocalTie
 extends QueueOrderPolicyLocalBase
 {

  private QueueOrderPolicyOperations _delegate;
  public QueueOrderPolicyLocalTie(QueueOrderPolicyOperations delegate) {
    this._delegate = delegate;
  };

  public QueueOrderPolicyOperations _delegate() {
    return this._delegate;
  };

  public short allowed_orders() {
    return this._delegate.allowed_orders();
  }

  public int policy_type() {
    return this._delegate.policy_type();
  }

  public org.omg.CORBA.Policy copy() {
    return this._delegate.copy(
    );
  };

  public void destroy() {
    this._delegate.destroy(
    );
  };



}
