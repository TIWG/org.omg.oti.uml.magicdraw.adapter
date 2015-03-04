package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLEvent 
  extends UMLEvent[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#Event
  import ops._

  override def event_trigger = e.get_triggerOfEvent.toSet[Uml#Trigger]
}
