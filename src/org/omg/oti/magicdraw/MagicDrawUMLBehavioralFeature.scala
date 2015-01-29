package org.omg.oti.magicdraw

import org.omg.oti.UMLBehavioralFeature

import scala.collection.JavaConversions._

trait MagicDrawUMLBehavioralFeature extends UMLBehavioralFeature[MagicDrawUML] with MagicDrawUMLFeature with MagicDrawUMLNamespace {
  override protected def e: Uml#BehavioralFeature
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def ownedParameters = umlParameter(e.getOwnedParameter.toIterator).toSeq
}