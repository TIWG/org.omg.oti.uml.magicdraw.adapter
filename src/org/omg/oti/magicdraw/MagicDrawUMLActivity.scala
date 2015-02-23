package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivity 
  extends UMLActivity[MagicDrawUML]
  with MagicDrawUMLBehavior {

  override protected def e: Uml#Activity
  import ops._

  override def isReadOnly = e.isReadOnly
  
  override def isSingleExecution = e.isSingleExecution
  
  override def partition = e.getPartition.toSet[Uml#ActivityPartition]
  
  override def structuredNode = e.getStructuredNode.toSet[Uml#StructuredActivityNode]
  
}
