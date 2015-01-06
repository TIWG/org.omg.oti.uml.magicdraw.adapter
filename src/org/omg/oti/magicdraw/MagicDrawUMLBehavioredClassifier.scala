package org.omg.oti.magicdraw

import org.omg.oti.UMLBehavioredClassifier

trait MagicDrawUMLBehavioredClassifier extends UMLBehavioredClassifier[MagicDrawUML] with MagicDrawUMLClassifier {
  override protected def e: Uml#BehavioredClassifier
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}