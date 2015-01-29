package org.omg.oti.magicdraw

import org.omg.oti.UMLProperty

import scala.collection.JavaConversions._

trait MagicDrawUMLProperty extends UMLProperty[MagicDrawUML] with MagicDrawUMLStructuralFeature with MagicDrawUMLConnectableElement {
  override protected def e: Uml#Property
 
  implicit val ops: MagicDrawUMLUtil
  import ops._  
  
  def isComposite = e.isComposite()
  
  def opposite = memberEndOfAssociation match {
    case None => None
    case Some( a ) => Some( a.memberEnds.filter(_ != this).next() )
  }
  
  def navigableOwnedEndOfAssociation = Option.apply( e.get_associationOfNavigableOwnedEnd )
  
  def memberEndOfAssociation = Option.apply( e.getAssociation )
   
  def subsettedProperties = e.getSubsettedProperty.toIterator
  override def redefinedProperties = e.getRedefinedProperty.toIterator
  
}