package org.omg.oti.magicdraw

import org.omg.oti.UMLFeature

trait MagicDrawUMLFeature extends UMLFeature[MagicDrawUML] with MagicDrawUMLRedefinableElement {
  override protected def e: Uml#Feature
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}