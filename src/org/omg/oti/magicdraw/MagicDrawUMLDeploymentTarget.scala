package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDeploymentTarget 
  extends UMLDeploymentTarget[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#DeploymentTarget
  import ops._

}
