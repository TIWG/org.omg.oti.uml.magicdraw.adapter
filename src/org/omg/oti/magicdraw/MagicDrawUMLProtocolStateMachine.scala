package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLProtocolStateMachine 
  extends UMLProtocolStateMachine[MagicDrawUML]
  with MagicDrawUMLStateMachine {

  override protected def e: Uml#ProtocolStateMachine
  import ops._

}
