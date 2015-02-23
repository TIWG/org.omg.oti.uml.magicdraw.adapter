package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLMessage 
  extends UMLMessage[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#Message
  import ops._

}
