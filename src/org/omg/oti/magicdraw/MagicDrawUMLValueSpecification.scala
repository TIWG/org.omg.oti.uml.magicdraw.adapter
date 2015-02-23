package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLValueSpecification 
  extends UMLValueSpecification[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLTypedElement {

  override protected def e: Uml#ValueSpecification
  import ops._

  
}