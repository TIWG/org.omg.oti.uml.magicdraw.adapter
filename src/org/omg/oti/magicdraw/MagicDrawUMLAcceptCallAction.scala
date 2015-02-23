package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLAcceptCallAction 
  extends UMLAcceptCallAction[MagicDrawUML]
  with MagicDrawUMLAcceptEventAction {

  override protected def e: Uml#AcceptCallAction
  import ops._

  override def returnInformation = Option.apply( e.getReturnInformation )
}
