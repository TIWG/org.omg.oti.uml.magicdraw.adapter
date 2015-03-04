package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

import scala.collection.JavaConversions._

trait MagicDrawUMLAssociation 
  extends UMLAssociation[MagicDrawUML]
  with MagicDrawUMLClassifier
  with MagicDrawUMLRelationship {
  
  override protected def e: Uml#Association
  import ops._
  
  override def isDerived = e.isDerived
  
  override def ownedEnd = e.getOwnedEnd.toIterable
  
  override def navigableOwnedEnd = e.getNavigableOwnedEnd.toSet[Uml#Property]
  
  override def memberEnd = e.getMemberEnd.toSeq
  
  override def type_connector = e.get_connectorOfType.toSet[Uml#Connector]
    
  override def association_clearAssociationAction =
    Option.apply( e.get_clearAssociationActionOfAssociation )

}