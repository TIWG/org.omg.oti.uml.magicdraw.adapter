package org.omg.oti.magicdraw

import org.omg.oti.UMLNamedElement

import scala.collection.JavaConversions._

trait MagicDrawUMLNamedElement extends UMLNamedElement[MagicDrawUML] with MagicDrawUMLElement {
  override protected def e: Uml#NamedElement
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def name = Option.apply( e.getName )  
  def setName( name: String ) = e.setName( name )
  
  def qualifiedName = Option.apply( e.getQualifiedName )
  
  def memberOfMemberNamespaces = e.get_namespaceOfMember.toIterator
  def ownedMemberOfNamespaces = Option.apply( e.getNamespace )

}