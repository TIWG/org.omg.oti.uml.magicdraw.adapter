package org.omg.oti.magicdraw

import org.omg.oti.UMLUseCase

trait MagicDrawUMLUseCase extends UMLUseCase[MagicDrawUML] with MagicDrawUMLBehavioredClassifier {
  override protected def e: Uml#UseCase
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}