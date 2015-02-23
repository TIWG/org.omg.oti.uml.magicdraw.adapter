package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLSlot 
  extends UMLSlot[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#Slot
  import ops._
  
  def values = e.getValue.toIterable
  def definingFeature = Option.apply( e.getDefiningFeature )

}