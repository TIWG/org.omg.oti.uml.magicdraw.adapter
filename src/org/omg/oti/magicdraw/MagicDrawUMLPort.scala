package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLPort 
  extends UMLPort[MagicDrawUML]
  with MagicDrawUMLProperty {

  import ops._
  override protected def e: Uml#Port

  
}