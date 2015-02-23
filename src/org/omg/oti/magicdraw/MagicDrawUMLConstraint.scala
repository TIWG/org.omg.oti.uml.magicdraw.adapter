package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLConstraint 
  extends UMLConstraint[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#Constraint
  import ops._
  
  override def constrainedElement = for { c <- e.getConstrainedElement } yield umlElement( c )
}