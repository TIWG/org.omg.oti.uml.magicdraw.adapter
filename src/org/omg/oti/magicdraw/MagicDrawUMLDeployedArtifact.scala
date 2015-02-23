package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDeployedArtifact 
  extends UMLDeployedArtifact[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#DeployedArtifact
  import ops._

}
