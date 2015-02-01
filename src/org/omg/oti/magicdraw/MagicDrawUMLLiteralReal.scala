package org.omg.oti.magicdraw

import org.omg.oti.UMLLiteralReal

trait MagicDrawUMLLiteralReal extends UMLLiteralReal[MagicDrawUML] with MagicDrawUMLLiteralSpecification {
  override protected def e: Uml#LiteralReal
  
  import ops._
  
  def value: Option[Double] = Some( e.getValue )
  
}