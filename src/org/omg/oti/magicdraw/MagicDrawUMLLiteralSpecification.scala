package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLLiteralSpecification 
  extends UMLLiteralSpecification[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#LiteralSpecification
  import ops._
}