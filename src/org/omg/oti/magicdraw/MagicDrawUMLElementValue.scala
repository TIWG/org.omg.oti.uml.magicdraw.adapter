package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLElementValue 
  extends MagicDrawUMLValueSpecification {
  
  override protected def e: Uml#ElementValue
  import ops._
  
  def element: Option[UMLElement[Uml]] = Option.apply( e.getElement )
    
  override def metaAttributes: MetaAttributeFunctions =
    valueSpecification_metaAttributes
    
  override def forwardReferencesFromMetamodelAssociations = 
    element_forwardReferencesFromMetamodelAssociations ++
    element
    
  override def compositeMetaProperties: MetaPropertyFunctions =
    valueSpecification_compositeMetaProperties
    
  override def referenceMetaProperties: MetaPropertyFunctions =
    valueSpecification_referenceMetaProperties ++
    Seq( MetaPropertyReference[Uml, MagicDrawUMLElementValue, UMLElement[Uml]]( "element", _.element ) )
    
  override def asForwardReferencesToImportableOuterPackageableElements: Set[UMLPackageableElement[Uml]] = 
    element.fold(Set[UMLPackageableElement[Uml]]())(_.asForwardReferencesToImportableOuterPackageableElements)

  override def forwardReferencesFromStereotypeTagValue: Set[UMLElement[Uml]] = 
    element.toSet
}