package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLComponentRealization 
  extends UMLComponentRealization[MagicDrawUML]
  with MagicDrawUMLRealization {

  override protected def e: Uml#ComponentRealization
  import ops._

}
