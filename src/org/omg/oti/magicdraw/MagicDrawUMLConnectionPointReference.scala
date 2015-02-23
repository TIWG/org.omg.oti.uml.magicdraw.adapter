package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLConnectionPointReference 
  extends UMLConnectionPointReference[MagicDrawUML]
  with MagicDrawUMLVertex {

  override protected def e: Uml#ConnectionPointReference
  import ops._

}
