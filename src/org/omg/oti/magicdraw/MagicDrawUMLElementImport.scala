package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLElementImport 
  extends UMLElementImport[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#ElementImport
  import ops._

}
