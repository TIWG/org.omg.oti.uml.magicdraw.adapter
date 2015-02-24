package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityPartition 
  extends UMLActivityPartition[MagicDrawUML]
  with MagicDrawUMLActivityGroup {

  override protected def e: Uml#ActivityPartition
  import ops._

  override def edge: Set[UMLActivityEdge[Uml]] =
    e.getEdge.toSet[Uml#ActivityEdge]
  
  override def isDimension =
    e.isDimension
    
  override def isExternal =
    e.isExternal
    
  override def node: Set[UMLActivityNode[Uml]] =
    e.getNode.toSet[Uml#ActivityNode]
  
  override def represents: Option[UMLElement[Uml]] =
    Option.apply( e.getRepresents )
    
  override def subpartition: Set[UMLActivityPartition[Uml]] =
    e.getSubpartition.toSet[Uml#ActivityPartition]
  
  override def superPartition: Option[UMLActivityPartition[Uml]] =
    Option.apply( e.getSuperPartition )
    
  override def partition_activity: Option[UMLActivity[Uml]] =
    Option.apply( e.get_activityOfPartition )
    
}
