package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLPseudostate 
  extends UMLPseudostate[MagicDrawUML]
  with MagicDrawUMLVertex {

  override protected def e: Uml#Pseudostate
  import ops._

}
