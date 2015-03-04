package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLMultiplicityElement 
  extends UMLMultiplicityElement[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#MultiplicityElement
  import ops._
  
  override def isOrdered = e.isOrdered
  
  override def isUnique = e.isUnique
  
  override def lower = e.getLower
  
  override def lowerValue: Option[UMLValueSpecification[Uml]] = Option.apply( e.getLowerValue )
  
  override def upper = e.getUpper
  
  override def upperValue: Option[UMLValueSpecification[Uml]] = Option.apply( e.getUpperValue )
  
}