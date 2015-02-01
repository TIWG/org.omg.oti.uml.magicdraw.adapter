package org.omg.oti.magicdraw

import org.omg.oti.UMLMultiplicityElement

trait MagicDrawUMLMultiplicityElement extends UMLMultiplicityElement[MagicDrawUML] with MagicDrawUMLElement {
  override protected def e: Uml#MultiplicityElement
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  override def isOrdered = e.isOrdered
  override def isUnique = e.isUnique
  
  def lower: Int = e.getLower
  def upper: Int = e.getUpper
}