package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLStringExpression extends UMLStringExpression[MagicDrawUML] with MagicDrawUMLExpression {
  override protected def e: Uml#StringExpression
  
  import ops._
  
  def subExpressions = e.getSubExpression.toSeq
}