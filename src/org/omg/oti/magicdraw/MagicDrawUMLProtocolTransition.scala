package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLProtocolTransition 
  extends UMLProtocolTransition[MagicDrawUML]
  with MagicDrawUMLTransition {

  override protected def e: Uml#ProtocolTransition
  import ops._

}
