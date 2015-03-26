package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInterfaceRealization 
  extends UMLInterfaceRealization[MagicDrawUML]
  with MagicDrawUMLRealization {

  override protected def e: Uml#InterfaceRealization
  import ops._
  
  override def contract: Option[UMLInterface[Uml]] =
    Option.apply(e.getContract)
    
  override def implementingClassifier: Option[UMLBehavioredClassifier[Uml]] =
    Option.apply(e.getImplementingClassifier)

}
