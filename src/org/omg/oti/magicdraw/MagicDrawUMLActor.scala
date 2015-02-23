package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActor 
  extends UMLActor[MagicDrawUML]
  with MagicDrawUMLBehavioredClassifier {

  override protected def e: Uml#Actor
  import ops._

}
