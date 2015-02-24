package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
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
    
	override def result: Iterable[UMLOutputPin[Uml]] =
    e.getResult.toIterable
    
}
