package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLConditionalNode 
  extends UMLConditionalNode[MagicDrawUML]
  with MagicDrawUMLStructuredActivityNode {

  override protected def e: Uml#ConditionalNode
  import ops._

  override def clause: Set[UMLClause[Uml]] =
    e.getClause.toSet[Uml#Clause]
  
  override def isAssured: Boolean =
    e.isAssured
    
  override def isDeterminate: Boolean =
    e.isDeterminate
    
	override def result: Seq[UMLOutputPin[Uml]] =
    e.getResult.toSeq
    
}
