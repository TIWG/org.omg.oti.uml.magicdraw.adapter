package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLObservation 
  extends UMLObservation[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#Observation
  import ops._

}
