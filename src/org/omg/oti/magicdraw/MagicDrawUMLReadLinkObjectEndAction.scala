package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReadLinkObjectEndAction 
  extends UMLReadLinkObjectEndAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadLinkObjectEndAction
  import ops._

}
