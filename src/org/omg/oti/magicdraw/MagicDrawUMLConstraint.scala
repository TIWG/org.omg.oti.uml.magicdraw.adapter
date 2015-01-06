package org.omg.oti.magicdraw

import org.omg.oti.UMLConstraint

import scala.collection.JavaConversions._

trait MagicDrawUMLConstraint extends UMLConstraint[MagicDrawUML] with MagicDrawUMLPackageableElement {
  override protected def e: Uml#Constraint
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}