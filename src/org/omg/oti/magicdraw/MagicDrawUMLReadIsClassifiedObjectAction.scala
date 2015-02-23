package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReadIsClassifiedObjectAction 
  extends UMLReadIsClassifiedObjectAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadIsClassifiedObjectAction
  import ops._

}
