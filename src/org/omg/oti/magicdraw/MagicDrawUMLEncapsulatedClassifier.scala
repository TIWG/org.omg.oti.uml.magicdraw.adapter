package org.omg.oti.magicdraw

import org.omg.oti.UMLEncapsulatedClassifier

trait MagicDrawUMLEncapsulatedClassifier extends UMLEncapsulatedClassifier[MagicDrawUML] with MagicDrawUMLStructuredClassifier {
  override protected def e: Uml#EncapsulatedClassifier
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}