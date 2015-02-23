package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLPin 
  extends UMLPin[MagicDrawUML]
  with MagicDrawUMLObjectNode
  with MagicDrawUMLMultiplicityElement {

  override protected def e: Uml#Pin
  import ops._

}
