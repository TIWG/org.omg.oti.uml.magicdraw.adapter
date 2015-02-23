package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityGroup 
  extends UMLActivityGroup[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#ActivityGroup
  import ops._

}
