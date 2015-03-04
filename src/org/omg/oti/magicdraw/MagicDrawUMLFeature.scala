package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLFeature 
  extends UMLFeature[MagicDrawUML]
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#Feature
  import ops._

  override def isStatic = e.isStatic
}
