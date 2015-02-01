package org.omg.oti.magicdraw

import org.omg.oti.UMLSlot

import scala.collection.JavaConversions._

trait MagicDrawUMLSlot extends UMLSlot[MagicDrawUML] with MagicDrawUMLElement {
  override protected def e: Uml#Slot
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def values = e.getValue.toIterable
  def definingFeature = Option.apply( e.getDefiningFeature )

}