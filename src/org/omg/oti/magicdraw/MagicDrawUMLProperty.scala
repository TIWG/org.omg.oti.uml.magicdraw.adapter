package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._
import scala.language.postfixOps

trait MagicDrawUMLProperty extends UMLProperty[MagicDrawUML] with MagicDrawUMLStructuralFeature with MagicDrawUMLConnectableElement {
  override protected def e: Uml#Property
 
  import ops._  
  
  override def isComposite = e.isComposite()
  override def isDerived = e.isDerived()  
  override def isUnion = e.isDerivedUnion()  
  override def isID = e.isID()
    
  def defaultValue = Option.apply( e.getDefaultValue )
  
  def opposite = association_memberEnd match {
    case None => None
    case Some( a ) => a.memberEnds filter (_ != this) headOption
  }
  
  def association_navigableOwnedEnd = Option.apply( e.get_associationOfNavigableOwnedEnd )
  
  def association_memberEnd = Option.apply( e.getAssociation )
   
  def subsettedProperties = e.getSubsettedProperty.toIterable
  override def redefinedProperties = e.getRedefinedProperty.toIterable
  
}