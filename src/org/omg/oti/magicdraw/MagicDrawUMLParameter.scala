package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLParameter 
  extends UMLParameter[MagicDrawUML]
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLMultiplicityElement {

  import ops._
  override protected def e: Uml#Parameter
  
}