package org.omg.oti.magicdraw

import org.omg.oti.UMLClassifier

import scala.collection.JavaConversions._

trait MagicDrawUMLClassifier extends UMLClassifier[MagicDrawUML] with MagicDrawUMLNamespace with MagicDrawUMLType {
  override protected def e: Uml#Classifier
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def classifierOfInstanceSpecifications = umlInstanceSpecification( e.get_instanceSpecificationOfClassifier.toSet )
}