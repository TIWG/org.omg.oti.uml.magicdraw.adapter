package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLProtocolConformance 
  extends UMLProtocolConformance[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#ProtocolConformance
  import ops._

}
