package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLConnectorEnd 
  extends UMLConnectorEnd[MagicDrawUML]
  with MagicDrawUMLMultiplicityElement {

  override protected def e: Uml#ConnectorEnd
  import ops._
  
  override def definingEnd = Option.apply(e.getDefiningEnd)
  
  override def partWithPort = Option.apply(e.getPartWithPort)
  
  override def role = Option.apply(e.getRole)
  
}