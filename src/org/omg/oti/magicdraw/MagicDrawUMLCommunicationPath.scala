package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLCommunicationPath 
  extends UMLCommunicationPath[MagicDrawUML]
  with MagicDrawUMLAssociation {

  override protected def e: Uml#CommunicationPath
  import ops._

}
