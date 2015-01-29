package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.UMLElement

trait MagicDrawUMLElementValue extends MagicDrawUMLValueSpecification {
  override protected def e: Uml#ElementValue
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def element: Option[UMLElement[Uml]] = Option.apply( e.getElement )
    
  override def forwardReferencesFromMetamodelAssociations = 
    element_forwardReferencesFromMetamodelAssociations ++
    element
}