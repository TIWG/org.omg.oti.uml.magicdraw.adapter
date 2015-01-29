package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

trait MagicDrawUMLDiagram extends MagicDrawUMLNamedElement {
  override protected def e: Uml#Diagram
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
    
  override def forwardReferencesFromMetamodelAssociations = 
    namedElement_forwardReferencesFromMetamodelAssociations
}