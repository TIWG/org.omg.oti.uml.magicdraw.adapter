package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLAssociationClass 
  extends UMLAssociationClass[MagicDrawUML]
  with MagicDrawUMLClass
  with MagicDrawUMLAssociation {

  override protected def e: Uml#AssociationClass
  import ops._

}