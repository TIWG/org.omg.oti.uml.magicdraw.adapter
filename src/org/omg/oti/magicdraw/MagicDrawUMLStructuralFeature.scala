package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLStructuralFeature 
  extends UMLStructuralFeature[MagicDrawUML]
  with MagicDrawUMLFeature
  with MagicDrawUMLTypedElement
  with MagicDrawUMLMultiplicityElement {

  override protected def e: Uml#StructuralFeature
  import ops._
 
  override def isReadOnly = e.isReadOnly
  
  override def definingFeature_slot = e.get_slotOfDefiningFeature.toSet[Uml#Slot]
  
  override def structuralFeature_structuralFeatureAction = e.get_structuralFeatureActionOfStructuralFeature.toSet[Uml#StructuralFeatureAction]
  
}