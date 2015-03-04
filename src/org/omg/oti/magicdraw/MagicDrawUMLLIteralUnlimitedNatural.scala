package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLLiteralUnlimitedNatural 
  extends UMLLiteralUnlimitedNatural[MagicDrawUML]
  with MagicDrawUMLLiteralSpecification {

  override protected def e: Uml#LiteralUnlimitedNatural
  import ops._
  
  override def value = e.getValue
}