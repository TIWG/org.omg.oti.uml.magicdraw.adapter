package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTransition 
  extends UMLTransition[MagicDrawUML]
  with MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#Transition
  import ops._

}
