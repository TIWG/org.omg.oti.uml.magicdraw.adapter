package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLRegion 
  extends UMLRegion[MagicDrawUML]
  with MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#Region
  import ops._

  // 14.37
  	override def redefinitionContext: Iterable[UMLClassifier[Uml]] = ???
    
}
