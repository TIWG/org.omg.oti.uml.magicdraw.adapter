package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLLiteralBoolean 
  extends UMLLiteralBoolean[MagicDrawUML]
  with MagicDrawUMLLiteralSpecification {

  override protected def e: Uml#LiteralBoolean
  import ops._
  
  override def value = e.isValue
  
}