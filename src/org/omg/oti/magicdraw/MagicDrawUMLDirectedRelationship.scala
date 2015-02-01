package org.omg.oti.magicdraw

import org.omg.oti.UMLDirectedRelationship

import scala.collection.JavaConversions._

trait MagicDrawUMLDirectedRelationship extends UMLDirectedRelationship[MagicDrawUML] with MagicDrawUMLRelationship {
  override protected def e: Uml#DirectedRelationship
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def sources = e.getSource.toIterable
  def targets = e.getTarget.toIterable

}