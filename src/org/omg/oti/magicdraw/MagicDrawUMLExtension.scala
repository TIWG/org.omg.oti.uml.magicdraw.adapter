package org.omg.oti.magicdraw

import org.omg.oti._

trait MagicDrawUMLExtension extends UMLExtension[MagicDrawUML] with MagicDrawUMLAssociation {
  override protected def e: Uml#Extension
  
  import ops._
  
  override def ownedEnds: Iterable[UMLExtensionEnd[Uml]] = {
    val extensionOwnedEnds = super[MagicDrawUMLAssociation].ownedEnds.selectByKindOf { case ee: UMLExtensionEnd[Uml] => ee }
    require( extensionOwnedEnds.size <= 1 )
    extensionOwnedEnds
  }
}