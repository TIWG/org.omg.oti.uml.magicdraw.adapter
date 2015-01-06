package org.omg.oti.magicdraw

import org.omg.oti.UMLClass

trait MagicDrawUMLClass extends UMLClass[MagicDrawUML] with MagicDrawUMLEncapsulatedClassifier with MagicDrawUMLBehavioredClassifier {
  override protected def e: Uml#Class
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}