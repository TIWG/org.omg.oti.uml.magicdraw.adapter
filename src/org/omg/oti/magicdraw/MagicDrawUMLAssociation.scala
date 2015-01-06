package org.omg.oti.magicdraw

import org.omg.oti.UMLAssociation

import scala.collection.JavaConversions._

trait MagicDrawUMLAssociation extends UMLAssociation[MagicDrawUML] with MagicDrawUMLClassifier with MagicDrawUMLRelationship {
  override protected def e: Uml#Association
    
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def isDerived = e.isDerived
  def ownedEnds = e.getOwnedEnd.toIterator
  def navigableOwnedEnds = e.getNavigableOwnedEnd.toIterator
  def memberEnds = e.getMemberEnd.toIterator
  def endTypes = e.getEndType.toIterator
}