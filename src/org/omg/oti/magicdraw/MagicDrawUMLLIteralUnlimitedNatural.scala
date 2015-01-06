package org.omg.oti.magicdraw

import org.omg.oti.UMLLiteralUnlimitedNatural

trait MagicDrawUMLLiteralUnlimitedNatural extends UMLLiteralUnlimitedNatural[MagicDrawUML] with MagicDrawUMLLiteralSpecification {
  override protected def e: Uml#LiteralUnlimitedNatural
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}