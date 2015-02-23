package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLifeline 
  extends UMLLifeline[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#Lifeline
  import ops._

}
