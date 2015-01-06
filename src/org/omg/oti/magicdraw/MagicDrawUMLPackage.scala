package org.omg.oti.magicdraw

import org.omg.oti.UMLPackage

trait MagicDrawUMLPackage extends UMLPackage[MagicDrawUML] with MagicDrawUMLNamespace with MagicDrawUMLPackageableElement {
  override protected def e: Uml#Package
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}