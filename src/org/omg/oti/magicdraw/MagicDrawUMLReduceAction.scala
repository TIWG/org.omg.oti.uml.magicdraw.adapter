package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReduceAction 
  extends UMLReduceAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReduceAction
  import ops._

  // 16.56
  override def collection: Option[UMLInputPin[Uml]] = ??? 
  
  // 16.56
  override def isOrdered: Boolean = ???
  
  // 16.56
  override def reducer: Option[UMLBehavior[Uml]] = ???
  
  // 16.56
	override def result: Option[UMLOutputPin[Uml]] = ???
  
}
