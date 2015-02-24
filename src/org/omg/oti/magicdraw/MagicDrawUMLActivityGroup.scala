package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityGroup 
  extends UMLActivityGroup[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#ActivityGroup
  import ops._

  override def containedEdge: Set[UMLActivityEdge[Uml]] =
    e.getContainedEdge.toSet[Uml#ActivityEdge]
  
	override def containedNode: Set[UMLActivityNode[Uml]] =
    e.getContainedNode.toSet[Uml#ActivityNode]
  
}
