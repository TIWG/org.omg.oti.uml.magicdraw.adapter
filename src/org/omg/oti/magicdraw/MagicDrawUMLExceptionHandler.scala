package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLExceptionHandler 
  extends UMLExceptionHandler[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#ExceptionHandler
  import ops._

  override def exceptionInput: Option[UMLObjectNode[Uml]] = ???
  
	override def exceptionType: Set[UMLClassifier[Uml]] = ??? 
  
	override def handlerBody: Option[UMLExecutableNode[Uml]] = ???
  
	override def protectedNode: Option[UMLExecutableNode[Uml]] = ???
  
}
