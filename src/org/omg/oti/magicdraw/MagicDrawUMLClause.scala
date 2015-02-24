package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLClause 
  extends UMLClause[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#Clause
  import ops._

	override def body: Set[UMLExecutableNode[Uml]] = ???
  
  override def bodyOutput: Seq[UMLOutputPin[Uml]] = ???
  
  override def decider: Option[UMLOutputPin[Uml]] = ???
  
  override def predecessorClause: Set[UMLClause[Uml]] = ???
  
  override def successorClause: Set[UMLClause[Uml]] = ???
  
	override def test: Set[UMLExecutableNode[Uml]] = ???
  
  
  

}
