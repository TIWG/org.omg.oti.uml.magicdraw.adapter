package org.omg.oti.magicdraw

import org.omg.oti.UMLInstanceSpecification

import scala.collection.JavaConversions._

trait MagicDrawUMLInstanceSpecification extends UMLInstanceSpecification[MagicDrawUML] with MagicDrawUMLPackageableElement {
  override protected def e: Uml#InstanceSpecification
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def isMagicDrawUMLAppliedStereotypeInstance: Boolean = e == e.getOwner.getAppliedStereotypeInstance
  
  def specification = Option.apply( e.getSpecification )
  def slots = e.getSlot.toIterator
  def classifiers = e.getClassifier.toIterator
  def instanceOfInstanceValues = e.get_instanceValueOfInstance.toIterator
}