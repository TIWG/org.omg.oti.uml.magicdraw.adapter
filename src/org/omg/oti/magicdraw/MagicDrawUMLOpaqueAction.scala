package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLOpaqueAction 
  extends UMLOpaqueAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#OpaqueAction
  import ops._

  override def body: Seq[String] =
    e.getBody.toSeq
   
	override def inputValue: Set[UMLInputPin[Uml]] =
    e.getInputValue.toSet[Uml#InputPin]
  
  override def language: Seq[String] =
    e.getLanguage.toSeq
  
  override def outputValue: Set[UMLOutputPin[Uml]] =
    e.getOutputValue.toSet[Uml#OutputPin]
  
}
