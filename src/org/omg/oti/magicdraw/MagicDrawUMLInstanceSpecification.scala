package org.omg.oti.magicdraw

import org.omg.oti.UMLInstanceSpecification

import scala.collection.JavaConversions._

trait MagicDrawUMLInstanceSpecification extends UMLInstanceSpecification[MagicDrawUML] with MagicDrawUMLPackageableElement {
  override protected def e: Uml#InstanceSpecification
  
  import ops._
  
  def isMagicDrawUMLAppliedStereotypeInstance: Boolean = e == e.getOwner.getAppliedStereotypeInstance
  
  def specification = Option.apply( e.getSpecification )
  def slots = e.getSlot.toIterable
  def classifiers = e.getClassifier.toIterable
  def instanceValues = e.get_instanceValueOfInstance.toIterable
}