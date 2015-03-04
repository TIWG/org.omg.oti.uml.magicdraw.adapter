package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLCombinedFragment 
  extends UMLCombinedFragment[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#CombinedFragment
  import ops._

  override def cfragmentGate: Set[UMLGate[Uml]] = ???
  
  override def interactionOperator: UMLInteractionOperatorKind.Value = ???
  
  override def operand: Seq[UMLInteractionOperand[Uml]] = ??? 

}
