package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLDataStoreNode 
  extends UMLDataStoreNode[MagicDrawUML]
  with MagicDrawUMLCentralBufferNode {

  override protected def e: Uml#DataStoreNode
  import ops._

}
