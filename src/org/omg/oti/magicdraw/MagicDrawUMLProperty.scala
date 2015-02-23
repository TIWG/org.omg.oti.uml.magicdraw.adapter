package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.postfixOps

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLProperty 
  extends UMLProperty[MagicDrawUML]
  with MagicDrawUMLStructuralFeature
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLDeploymentTarget {

  override protected def e: Uml#Property
  import ops._
  
  override def isComposite = e.isComposite()
  override def isDerived = e.isDerived()  
  override def isDerivedUnion = e.isDerivedUnion()  
  override def isID = e.isID()
    
  def defaultValue = Option.apply( e.getDefaultValue )
  
  override def opposite = association match {
    case None => None
    case Some( a ) => a.memberEnd filter (_ != this) headOption
  }
  
  override def navigableOwnedEnd_association = Option.apply( e.get_associationOfNavigableOwnedEnd )
  
  override def association = Option.apply( e.getAssociation )
   
  override def associationEnd = Option.apply( e.getAssociationEnd )
  
  override def subsettedProperty = e.getSubsettedProperty.toSet[Uml#Property]
  
}