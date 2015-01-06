package org.omg.oti.magicdraw

import org.omg.oti.UMLPackageableElement

trait MagicDrawUMLPackageableElement extends UMLPackageableElement[MagicDrawUML] with MagicDrawUMLNamedElement {
  override protected def e: MagicDrawUML#PackageableElement
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}