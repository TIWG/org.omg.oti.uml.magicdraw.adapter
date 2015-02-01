package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLOpaqueExpression extends UMLOpaqueExpression[MagicDrawUML] with MagicDrawUMLValueSpecification {
  override protected def e: Uml#OpaqueExpression
  
  import ops._
  
  def body = e.getBody.toSeq
  def language = e.getLanguage.toSeq
  
}