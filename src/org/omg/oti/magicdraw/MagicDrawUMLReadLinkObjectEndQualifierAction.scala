package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReadLinkObjectEndQualifierAction 
  extends UMLReadLinkObjectEndQualifierAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadLinkObjectEndQualifierAction
  import ops._

}
