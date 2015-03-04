package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLPartDecomposition 
  extends UMLPartDecomposition[MagicDrawUML]
  with MagicDrawUMLInteractionUse {

  override protected def e: Uml#PartDecomposition
  import ops._

  // 17.18
	override def decomposedAs_lifeline: Option[UMLLifeline[Uml]] = ???
  
}
