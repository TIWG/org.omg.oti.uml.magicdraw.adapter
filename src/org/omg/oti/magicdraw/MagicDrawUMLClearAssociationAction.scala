package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLClearAssociationAction 
  extends UMLClearAssociationAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ClearAssociationAction
  import ops._

}
