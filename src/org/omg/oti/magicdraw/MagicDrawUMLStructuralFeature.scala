package org.omg.oti.magicdraw

import org.omg.oti.UMLStructuralFeature

trait MagicDrawUMLStructuralFeature extends UMLStructuralFeature[MagicDrawUML] with MagicDrawUMLFeature with MagicDrawUMLTypedElement with MagicDrawUMLMultiplicityElement {
  override protected def e: Uml#StructuralFeature
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}