package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLValueSpecification 
  extends UMLValueSpecification[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLTypedElement {

  override protected def e: Uml#ValueSpecification
  import ops._

  override def min_interval =
    e.get_intervalOfMin.toIterable
    
  override def max_interval =
    e.get_intervalOfMax.toIterable
    
}