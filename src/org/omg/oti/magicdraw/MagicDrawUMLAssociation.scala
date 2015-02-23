package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

import scala.collection.JavaConversions._

trait MagicDrawUMLAssociation 
  extends UMLAssociation[MagicDrawUML]
  with MagicDrawUMLClassifier
  with MagicDrawUMLRelationship {
  
  override protected def e: Uml#Association
  import ops._
  
  override def isDerived = e.isDerived
  def ownedEnds = e.getOwnedEnd.toIterable
  def navigableOwnedEnds = e.getNavigableOwnedEnd.toIterable
  def memberEnds = e.getMemberEnd.toIterable
  def endTypes = e.getEndType.toIterable
}