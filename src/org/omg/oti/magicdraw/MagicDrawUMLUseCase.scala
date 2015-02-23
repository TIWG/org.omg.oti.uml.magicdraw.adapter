package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLUseCase 
  extends UMLUseCase[MagicDrawUML]
  with MagicDrawUMLBehavioredClassifier {

  override protected def e: Uml#UseCase
  import ops._

}