package org.omg.oti.magicdraw

import org.omg.oti.UMLAssociationClass

trait MagicDrawUMLAssociationClass extends UMLAssociationClass[MagicDrawUML] with MagicDrawUMLClass with MagicDrawUMLAssociation {
  override protected def e: Uml#AssociationClass
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}