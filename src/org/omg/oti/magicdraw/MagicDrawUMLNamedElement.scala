package org.omg.oti.magicdraw

import org.omg.oti.UMLNamedElement

import scala.collection.JavaConversions._

trait MagicDrawUMLNamedElement extends UMLNamedElement[MagicDrawUML] with MagicDrawUMLElement {
  override protected def e: Uml#NamedElement
  
  import ops._
  
  def name = Option.apply( e.getName )  
  def setName( name: String ) = e.setName( name )
  
  def qualifiedName = Option.apply( e.getQualifiedName )
  
  def memberNamespaces = e.get_namespaceOfMember.toIterable
  override def namespace = Option.apply( e.getNamespace )

  def supplierDependencies = e.getSupplierDependency.toIterable
  def clientDependencies = e.getClientDependency.toIterable
}