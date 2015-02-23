package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReadExtentAction 
  extends UMLReadExtentAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadExtentAction
  import ops._

}
