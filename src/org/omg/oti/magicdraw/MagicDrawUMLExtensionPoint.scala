package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExtensionPoint 
  extends UMLExtensionPoint[MagicDrawUML]
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#ExtensionPoint
  import ops._

  override def extensionLocation_extension: Set[UMLExtend[Uml]] = ???
  
}
