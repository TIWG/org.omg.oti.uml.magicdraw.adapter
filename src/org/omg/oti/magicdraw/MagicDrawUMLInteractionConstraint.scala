package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInteractionConstraint 
  extends UMLInteractionConstraint[MagicDrawUML]
  with MagicDrawUMLConstraint {

  override protected def e: Uml#InteractionConstraint
  import ops._

  // 17.11
	override def maxint: Option[UMLValueSpecification[Uml]] = ???
  
  // 17.11  
	override def minint: Option[UMLValueSpecification[Uml]] = ???
  
}
