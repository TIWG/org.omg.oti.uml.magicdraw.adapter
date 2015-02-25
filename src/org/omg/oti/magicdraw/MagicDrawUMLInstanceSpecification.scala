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
  
  override def specification = 
    Option.apply( e.getSpecification )
    
  override def slot = 
    e.getSlot.toSet[Uml#Slot]
  
  override def classifier = 
    e.getClassifier.toIterable
  
  override def instance_instanceValue =
    e.get_instanceValueOfInstance.toSet[Uml#InstanceValue]
  
}

case class MagicDrawUMLInstanceSpecificationImpl( val e: MagicDrawUML#InstanceSpecification, ops: MagicDrawUMLUtil )
extends MagicDrawUMLInstanceSpecification
