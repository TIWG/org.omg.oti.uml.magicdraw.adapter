package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLContinuation 
  extends UMLContinuation[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#Continuation
  import ops._

  override def setting: Boolean =
    e.isSetting
    
}
