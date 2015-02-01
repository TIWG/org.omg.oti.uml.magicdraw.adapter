package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLDiagram extends MagicDrawUMLNamedElement {
  override protected def e: Uml#Diagram
  
  import ops._
    
  override def forwardReferencesFromMetamodelAssociations = 
    namedElement_forwardReferencesFromMetamodelAssociations
    
  override def compositeMetaProperties: MetaPropertyFunctions =
    namedElement_compositeMetaProperties
    
  override def referenceMetaProperties: MetaPropertyFunctions =
    namedElement_referenceMetaProperties
    
}