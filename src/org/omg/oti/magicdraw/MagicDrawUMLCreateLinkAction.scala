package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCreateLinkAction 
  extends UMLCreateLinkAction[MagicDrawUML]
  with MagicDrawUMLWriteLinkAction {

  override protected def e: Uml#CreateLinkAction
  import ops._

}
