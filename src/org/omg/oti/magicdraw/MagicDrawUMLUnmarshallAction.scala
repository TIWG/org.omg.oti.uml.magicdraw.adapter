package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLUnmarshallAction 
  extends UMLUnmarshallAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#UnmarshallAction
  import ops._

}
