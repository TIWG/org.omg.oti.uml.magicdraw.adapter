package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCreateObjectAction 
  extends UMLCreateObjectAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#CreateObjectAction
  import ops._

}
