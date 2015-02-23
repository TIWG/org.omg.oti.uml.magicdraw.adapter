package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLState 
  extends UMLState[MagicDrawUML]
  with MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement
  with MagicDrawUMLVertex {

  override protected def e: Uml#State
  import ops._

}
