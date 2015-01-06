package org.omg.oti.magicdraw

import org.omg.oti.UMLNamespace
import org.omg.oti.UMLOps

import scala.collection.JavaConversions._

trait MagicDrawUMLNamespace extends UMLNamespace[MagicDrawUML] with MagicDrawUMLNamedElement {
  override protected def e: Uml#Namespace
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def members = umlNamedElement( e.getMember.toSet )

}