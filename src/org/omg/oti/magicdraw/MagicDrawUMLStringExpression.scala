package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLStringExpression 
  extends UMLStringExpression[MagicDrawUML]
  with MagicDrawUMLExpression
  with MagicDrawUMLTemplateableElement {

  override protected def e: Uml#StringExpression
  import ops._
  
  // 8.2
  override def subExpression = e.getSubExpression.toSeq
}