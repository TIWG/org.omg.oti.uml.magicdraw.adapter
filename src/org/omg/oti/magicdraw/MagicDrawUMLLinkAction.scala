package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLinkAction 
  extends UMLLinkAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#LinkAction
  import ops._

}
