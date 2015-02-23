package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExtension 
  extends UMLExtension[MagicDrawUML]
  with MagicDrawUMLAssociation {

  override protected def e: Uml#Extension
  import ops._
  
  override def ownedEnd = {
    val extensionOwnedEnds = super[MagicDrawUMLAssociation].ownedEnd.selectByKindOf { case ee: UMLExtensionEnd[Uml] => ee }
    require( extensionOwnedEnds.size <= 1 )
    extensionOwnedEnds
  }
}