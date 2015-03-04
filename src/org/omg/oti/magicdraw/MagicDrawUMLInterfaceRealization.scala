package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInterfaceRealization 
  extends UMLInterfaceRealization[MagicDrawUML]
  with MagicDrawUMLRealization {

  override protected def e: Uml#InterfaceRealization
  import ops._

}
