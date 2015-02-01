package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLExpression extends UMLExpression[MagicDrawUML] with MagicDrawUMLValueSpecification {
  override protected def e: Uml#Expression
  
  import ops._
  
  def symbol = Option.apply( e.getSymbol )
  def operands = e.getOperand.toSeq
}