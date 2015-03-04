package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLSlot 
  extends UMLSlot[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#Slot
  import ops._
    
  // 9.27
  def definingFeature = 
    Option.apply( e.getDefiningFeature )

  // 9.27
  override def value = 
    e.getValue.toSeq
}