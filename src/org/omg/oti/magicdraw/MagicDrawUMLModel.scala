package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLModel 
  extends UMLModel[MagicDrawUML]
  with MagicDrawUMLPackage {

  override protected def e: Uml#Model
  import ops._
  
  // 12.1  
	override def viewpoint: Option[String] = ???
}