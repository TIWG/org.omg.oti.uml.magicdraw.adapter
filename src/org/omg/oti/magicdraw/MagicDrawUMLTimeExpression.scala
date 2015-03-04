package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTimeExpression 
  extends UMLTimeExpression[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#TimeExpression
  import ops._

  // 8.3  
	override def expr: Option[UMLValueSpecification[Uml]] = ???
  
  // 8.3
  override def observation: Set[UMLObservation[Uml]] = ??? 
  
  // 8.4  
	override def max_timeInterval: Set[UMLTimeInterval[Uml]] = ???
  
  // 8.4
	override def min_timeInterval: Set[UMLTimeInterval[Uml]] = ???
}
