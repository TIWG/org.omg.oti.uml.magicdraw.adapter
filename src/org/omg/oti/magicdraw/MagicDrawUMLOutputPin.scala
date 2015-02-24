package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLOutputPin 
  extends UMLOutputPin[MagicDrawUML]
  with MagicDrawUMLPin {

  override protected def e: Uml#OutputPin
  import ops._

	override def bodyOutput_clause: Set[UMLClause[Uml]] =
   e.get_clauseOfBodyOutput.toSet[Uml#Clause]
      
	override def decider_clause: Option[UMLClause[Uml]] =
    Option.apply( e.get_clauseOfDecider )

	override def decider_loopNode: Option[UMLLoopNode[Uml]] =
    Option.apply( e.get_loopNodeOfDecider )
  
	def bodyOutput_loopNode: Set[UMLLoopNode[Uml]] =
    e.get_loopNodeOfBodyOutput.toSet[Uml#LoopNode]

}
