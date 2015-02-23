package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReclassifyObjectAction 
  extends UMLReclassifyObjectAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReclassifyObjectAction
  import ops._

}
