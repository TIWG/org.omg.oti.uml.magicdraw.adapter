package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityNode 
  extends UMLActivityNode[MagicDrawUML]
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#ActivityNode
  import ops._

  override def inGroup =
    e.getInGroup.toSet[Uml#ActivityGroup]
  
  override def inInterruptibleRegion =
    e.getInInterruptibleRegion.toSet[Uml#InterruptibleActivityRegion]
  
  override def inPartition =
    e.getInPartition.toSet[Uml#ActivityPartition]
  
  override def incoming =
    e.getIncoming.toSet[Uml#ActivityEdge]
  
  override def outgoing =
    e.getOutgoing.toSet[Uml#ActivityEdge]
  
}
