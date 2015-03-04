package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLExecutableNode 
  extends UMLExecutableNode[MagicDrawUML]
  with MagicDrawUMLActivityNode {

  override protected def e: Uml#ExecutableNode
  import ops._

	override def test_loopNode: Option[UMLLoopNode[Uml]] =
    Option.apply( e.get_loopNodeOfTest )
    
  override def bodyPart_loopNode: Option[UMLLoopNode[Uml]] =
    Option.apply( e.get_loopNodeOfBodyPart )
    
  override def test_clause: Option[UMLClause[Uml]] =
    Option.apply( e.get_clauseOfTest )
    
  override def setupPart_loopNode: Option[UMLLoopNode[Uml]] =
    Option.apply( e.get_loopNodeOfSetupPart )
     
	override def handlerBody_exceptionHandler: Set[UMLExceptionHandler[Uml]] =
    e.get_exceptionHandlerOfHandlerBody.toSet[Uml#ExceptionHandler]
  
  override def body_clause: Option[UMLClause[Uml]] =
    Option.apply( e.get_clauseOfBody )

}
