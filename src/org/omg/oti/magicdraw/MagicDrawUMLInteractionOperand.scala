package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInteractionOperand 
  extends UMLInteractionOperand[MagicDrawUML]
  with MagicDrawUMLNamespace
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#InteractionOperand
  import ops._

}
