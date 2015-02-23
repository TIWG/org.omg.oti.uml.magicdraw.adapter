package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReadSelfAction 
  extends UMLReadSelfAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadSelfAction
  import ops._

}
