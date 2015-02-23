package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReduceAction 
  extends UMLReduceAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReduceAction
  import ops._

}
