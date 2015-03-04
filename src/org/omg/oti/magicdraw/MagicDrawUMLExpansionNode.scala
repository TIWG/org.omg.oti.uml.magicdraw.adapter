package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLExpansionNode 
  extends UMLExpansionNode[MagicDrawUML]
  with MagicDrawUMLObjectNode {

  override protected def e: Uml#ExpansionNode
  import ops._

  override def regionAsInput: Option[UMLExpansionRegion[Uml]] = ???
  
	override def regionAsOutput: Option[UMLExpansionRegion[Uml]] = ???
  
}
