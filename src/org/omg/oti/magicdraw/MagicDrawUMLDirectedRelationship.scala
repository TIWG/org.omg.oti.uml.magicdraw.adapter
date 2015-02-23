package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDirectedRelationship 
  extends UMLDirectedRelationship[MagicDrawUML]
  with MagicDrawUMLRelationship {

  override protected def e: Uml#DirectedRelationship
  import ops._
  
  def source = e.getSource.toSet[Uml#Element]
  def target = e.getTarget.toSet[Uml#Element]

}