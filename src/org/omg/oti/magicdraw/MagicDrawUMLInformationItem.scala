package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInformationItem 
  extends UMLInformationItem[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#InformationItem
  import ops._

  // 20.1
  override def represented: Set[UMLClassifier[Uml]] = ???
  
}
