package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLiteralString 
  extends UMLLiteralString[MagicDrawUML]
  with MagicDrawUMLLiteralSpecification {

  override protected def e: Uml#LiteralString
  import ops._
  
  override def value = Option.apply(e.getValue)
}