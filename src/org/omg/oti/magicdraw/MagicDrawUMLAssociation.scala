package org.omg.oti.magicdraw

import org.omg.oti.UMLAssociation

import scala.collection.JavaConversions._

trait MagicDrawUMLAssociation extends UMLAssociation[MagicDrawUML] with MagicDrawUMLClassifier with MagicDrawUMLRelationship {
  override protected def e: Uml#Association
    
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  override def isDerived = e.isDerived
  def ownedEnds = e.getOwnedEnd.toIterable
  def navigableOwnedEnds = e.getNavigableOwnedEnd.toIterable
  def memberEnds = e.getMemberEnd.toIterable
  def endTypes = e.getEndType.toIterable
}