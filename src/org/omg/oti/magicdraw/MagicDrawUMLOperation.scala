package org.omg.oti.magicdraw

import org.omg.oti.UMLOperation

trait MagicDrawUMLOperation extends UMLOperation[MagicDrawUML] with MagicDrawUMLBehavioralFeature {
  override protected def e: Uml#Operation
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}