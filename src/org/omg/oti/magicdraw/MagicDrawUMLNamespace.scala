package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLNamespace 
  extends UMLNamespace[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  import ops._
  override protected def e: Uml#Namespace
  
  override def member = e.getMember.toSet[Uml#NamedElement]

  override def ownedMember: Set[UMLNamedElement[Uml]] =
    e.getOwnedMember.toSet[Uml#NamedElement]
}