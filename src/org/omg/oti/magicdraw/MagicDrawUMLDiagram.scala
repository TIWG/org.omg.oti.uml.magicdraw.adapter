package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDiagram 
  extends MagicDrawUMLNamedElement {
  
  override protected def e: Uml#Diagram
  import ops._
    
  override def metaAttributes: MetaAttributeFunctions =
    namedElement_metaAttributes
    
  override def forwardReferencesFromMetamodelAssociations = 
    namedElement_forwardReferencesFromMetamodelAssociations
    
  override def compositeMetaProperties: MetaPropertyFunctions =
    namedElement_compositeMetaProperties
    
  override def referenceMetaProperties: MetaPropertyFunctions =
    namedElement_referenceMetaProperties
    
}