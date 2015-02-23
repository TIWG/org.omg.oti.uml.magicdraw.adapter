package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLStateMachine 
  extends UMLStateMachine[MagicDrawUML]
  with MagicDrawUMLBehavior {

  override protected def e: Uml#StateMachine
  import ops._

}
