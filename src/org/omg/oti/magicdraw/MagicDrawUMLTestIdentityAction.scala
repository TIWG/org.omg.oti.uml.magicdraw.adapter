package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTestIdentityAction 
  extends UMLTestIdentityAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#TestIdentityAction
  import ops._

}
