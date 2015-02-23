package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInteractionConstraint 
  extends UMLInteractionConstraint[MagicDrawUML]
  with MagicDrawUMLConstraint {

  override protected def e: Uml#InteractionConstraint
  import ops._

}
