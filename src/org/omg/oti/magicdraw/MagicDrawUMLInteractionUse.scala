package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInteractionUse 
  extends UMLInteractionUse[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#InteractionUse
  import ops._

}
