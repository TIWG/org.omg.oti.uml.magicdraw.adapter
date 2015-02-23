package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLFinalState 
  extends UMLFinalState[MagicDrawUML]
  with MagicDrawUMLState {

  override protected def e: Uml#FinalState
  import ops._

}
