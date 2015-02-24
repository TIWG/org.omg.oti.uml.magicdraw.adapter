package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLStateInvariant 
  extends UMLStateInvariant[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#StateInvariant
  import ops._

  // 17.6
	override def covered: Iterable[UMLLifeline[Uml]] = ??? 
  
  // 17.1
	override def invariant: Option[UMLConstraint[Uml]] = ??? 
}
