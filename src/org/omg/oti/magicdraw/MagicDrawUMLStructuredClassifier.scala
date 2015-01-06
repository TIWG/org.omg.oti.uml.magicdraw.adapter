package org.omg.oti.magicdraw

import org.omg.oti.UMLStructuredClassifier

trait MagicDrawUMLStructuredClassifier extends UMLStructuredClassifier[MagicDrawUML] with MagicDrawUMLClassifier {
  override protected def e: Uml#StructuredClassifier
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}