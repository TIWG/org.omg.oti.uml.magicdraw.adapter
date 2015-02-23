package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInformationFlow 
  extends UMLInformationFlow[MagicDrawUML]
  with MagicDrawUMLPackageableElement
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#InformationFlow
  import ops._

}
