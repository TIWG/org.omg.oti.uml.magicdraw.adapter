package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTrigger 
  extends UMLTrigger[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#Trigger
  import ops._

}
