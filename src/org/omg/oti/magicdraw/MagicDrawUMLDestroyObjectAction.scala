package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDestroyObjectAction 
  extends UMLDestroyObjectAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#DestroyObjectAction
  import ops._

}
