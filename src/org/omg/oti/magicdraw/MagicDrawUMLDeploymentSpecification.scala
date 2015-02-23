package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDeploymentSpecification 
  extends UMLDeploymentSpecification[MagicDrawUML]
  with MagicDrawUMLArtifact {

  override protected def e: Uml#DeploymentSpecification
  import ops._

}
