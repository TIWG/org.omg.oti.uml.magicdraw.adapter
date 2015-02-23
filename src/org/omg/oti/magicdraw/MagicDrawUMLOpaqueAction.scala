package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLOpaqueAction 
  extends UMLOpaqueAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#OpaqueAction
  import ops._

}
