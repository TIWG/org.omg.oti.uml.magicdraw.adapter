package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLModel 
  extends UMLModel[MagicDrawUML]
  with MagicDrawUMLPackage {

  override protected def e: Uml#Model
  import ops._
  
}