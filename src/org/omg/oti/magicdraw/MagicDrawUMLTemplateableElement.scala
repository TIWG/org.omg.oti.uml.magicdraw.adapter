package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateableElement 
  extends UMLTemplateableElement[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#TemplateableElement
  import ops._

  override def templateBinding = e.getTemplateBinding.toSet[Uml#TemplateBinding]
}
