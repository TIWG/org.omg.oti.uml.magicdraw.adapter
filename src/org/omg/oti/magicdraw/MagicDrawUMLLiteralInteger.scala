package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLiteralInteger 
  extends UMLLiteralInteger[MagicDrawUML]
  with MagicDrawUMLLiteralSpecification {

  override protected def e: Uml#LiteralInteger
  import ops._
  
  override def value = e.getValue
}