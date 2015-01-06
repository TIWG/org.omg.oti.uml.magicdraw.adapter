package org.omg.oti.magicdraw

import org.omg.oti.UMLValueSpecification

trait MagicDrawUMLValueSpecification extends UMLValueSpecification[MagicDrawUML] with MagicDrawUMLPackageableElement with MagicDrawUMLTypedElement {
  override protected def e: Uml#ValueSpecification
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}