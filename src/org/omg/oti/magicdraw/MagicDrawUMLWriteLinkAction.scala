package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLWriteLinkAction 
  extends UMLWriteLinkAction[MagicDrawUML]
  with MagicDrawUMLLinkAction {

  override protected def e: Uml#WriteLinkAction
  import ops._

}
