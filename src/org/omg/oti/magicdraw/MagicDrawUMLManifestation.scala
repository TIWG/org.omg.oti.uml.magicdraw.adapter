package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLManifestation 
  extends UMLManifestation[MagicDrawUML]
  with MagicDrawUMLAbstraction {

  override protected def e: Uml#Manifestation
  import ops._

  override def utilizedElement: Option[UMLPackageableElement[Uml]] = ???
  
  override def manifestation_artifact: Option[UMLArtifact[Uml]] = ???
  
}
