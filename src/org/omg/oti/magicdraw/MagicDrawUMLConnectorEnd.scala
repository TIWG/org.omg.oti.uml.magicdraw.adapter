package org.omg.oti.magicdraw

import org.omg.oti.UMLConnectorEnd

trait MagicDrawUMLConnectorEnd extends UMLConnectorEnd[MagicDrawUML] with MagicDrawUMLMultiplicityElement {
  override protected def e: Uml#ConnectorEnd
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}