package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLLiteralNull 
  extends UMLLiteralNull[MagicDrawUML]
  with MagicDrawUMLLiteralSpecification {

  override protected def e: Uml#LiteralNull
  import ops._
}