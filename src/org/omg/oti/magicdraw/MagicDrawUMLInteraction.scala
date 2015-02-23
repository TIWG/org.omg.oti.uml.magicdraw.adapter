package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInteraction 
  extends UMLInteraction[MagicDrawUML]
  with MagicDrawUMLBehavior
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#Interaction
  import ops._

}
