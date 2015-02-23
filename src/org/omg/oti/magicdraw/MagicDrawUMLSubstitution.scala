package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLSubstitution 
  extends UMLSubstitution[MagicDrawUML]
  with MagicDrawUMLRealization {

  override protected def e: Uml#Substitution
  import ops._

}
