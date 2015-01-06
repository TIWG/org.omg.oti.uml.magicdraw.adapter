package org.omg.oti.magicdraw

import org.omg.oti.UMLConnector

trait MagicDrawUMLConnector extends UMLConnector[MagicDrawUML] with MagicDrawUMLFeature {
  override protected def e: Uml#Connector
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}