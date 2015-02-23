package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExtensionPoint 
  extends UMLExtensionPoint[MagicDrawUML]
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#ExtensionPoint
  import ops._

}
