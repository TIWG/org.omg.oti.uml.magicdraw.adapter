package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInterruptibleActivityRegion 
  extends UMLInterruptibleActivityRegion[MagicDrawUML]
  with MagicDrawUMLActivityGroup {

  override protected def e: Uml#InterruptibleActivityRegion
  import ops._

  override def interruptingEdge: Set[UMLActivityEdge[Uml]] =
    e.getInterruptingEdge.toSet[Uml#ActivityEdge]
  
  override def node: Set[UMLActivityNode[Uml]] =
    e.getNode.toSet[Uml#ActivityNode]
  
}
