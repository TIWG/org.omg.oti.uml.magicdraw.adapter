package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInteractionOperand 
  extends UMLInteractionOperand[MagicDrawUML]
  with MagicDrawUMLNamespace
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#InteractionOperand
  import ops._

  // 17.11
	override def fragment: Seq[UMLInteractionFragment[Uml]] = ???
  
  // 17.11
	override def guard: Option[UMLInteractionConstraint[Uml]] = ???

}
