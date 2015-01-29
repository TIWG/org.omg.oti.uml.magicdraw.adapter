package org.omg.oti.magicdraw

import org.omg.oti._

trait MagicDrawUMLConnectorEnd extends UMLConnectorEnd[MagicDrawUML] with MagicDrawUMLMultiplicityElement {
  override protected def e: Uml#ConnectorEnd
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def definingEnd: Option[UMLProperty[Uml]] = Option.apply(e.getDefiningEnd)
  def role: Option[UMLConnectableElement[Uml]] = Option.apply(e.getRole)
  
}