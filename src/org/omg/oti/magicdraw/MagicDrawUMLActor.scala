package org.omg.oti.magicdraw

import org.omg.oti.UMLActor

trait MagicDrawUMLActor extends UMLActor[MagicDrawUML] with MagicDrawUMLBehavioredClassifier {
  override protected def e: Uml#Actor
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
}