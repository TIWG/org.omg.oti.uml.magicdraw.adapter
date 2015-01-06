package org.omg.oti.magicdraw

import org.omg.oti.UMLExtensionEnd

trait MagicDrawUMLExtensionEnd extends UMLExtensionEnd[MagicDrawUML] with MagicDrawUMLProperty {
  override protected def e: Uml#ExtensionEnd
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}