package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLStructuredClassifier 
  extends UMLStructuredClassifier[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#StructuredClassifier
  import ops._

  override def ownedAttribute = e.getOwnedAttribute.toSeq
  
  override def part = e.getPart.toSet[Uml#Property]
  
  override def role = e.getRole.toSet[Uml#ConnectableElement]
  
}