package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLDependency 
  extends UMLDependency[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#Dependency
  import ops._

  override def roleBinding_collaborationUse = Option.apply( e.get_collaborationUseOfRoleBinding )
}