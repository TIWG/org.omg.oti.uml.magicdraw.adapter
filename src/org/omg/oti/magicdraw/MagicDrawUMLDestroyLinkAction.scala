package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDestroyLinkAction 
  extends UMLDestroyLinkAction[MagicDrawUML]
  with MagicDrawUMLWriteLinkAction {

  override protected def e: Uml#DestroyLinkAction
  import ops._

}
