package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInstanceSpecification 
  extends UMLInstanceSpecification[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLDeploymentTarget
  with MagicDrawUMLDeployedArtifact {

  override protected def e: Uml#InstanceSpecification
  import ops._
  
  def getMagicDrawInstanceSpecification = e
  
  def isMagicDrawUMLAppliedStereotypeInstance: Boolean = e == e.getOwner.getAppliedStereotypeInstance
  
  def specification = Option.apply( e.getSpecification )
  def slots = e.getSlot.toIterable
  def classifiers = e.getClassifier.toIterable
  def instanceValues = e.get_instanceValueOfInstance.toIterable
}