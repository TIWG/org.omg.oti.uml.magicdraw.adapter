package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLOpaqueExpression 
  extends UMLOpaqueExpression[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#OpaqueExpression
  import ops._

  // 8.2
  override def behavior: Option[UMLBehavior[Uml]] = 
    Option.apply( e.getBehavior )
  
  // 8.2
  override def body = e.getBody.toSeq
  
  // 8.2
  override def language = e.getLanguage.toSeq
  
  // 8.2
  override def result: Option[UMLParameter[Uml]] = ???
  
}