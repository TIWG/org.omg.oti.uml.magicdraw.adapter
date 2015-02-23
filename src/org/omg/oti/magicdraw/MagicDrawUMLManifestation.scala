package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLManifestation 
  extends UMLManifestation[MagicDrawUML]
  with MagicDrawUMLAbstraction {

  override protected def e: Uml#Manifestation
  import ops._

}
