package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLSignal 
  extends UMLSignal[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#Signal
  import ops._

  // 10.5
  override def ownedAttribute: Seq[UMLProperty[Uml]] = ???
  
  // 10.5
  override def signal_reception: Set[UMLReception[Uml]] = ???
  
  // 16.13
  override def signal_broadcastSignalAction: Set[UMLBroadcastSignalAction[Uml]] = ???
  
  // 16.13
  override def signal_sendSignalAction: Set[UMLSendSignalAction[Uml]] = ???
  
  // 13.2
  override def signal_signalEvent: Set[UMLSignalEvent[Uml]] = ???
  
}
