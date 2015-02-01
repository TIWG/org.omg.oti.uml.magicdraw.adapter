package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLConnector extends UMLConnector[MagicDrawUML] with MagicDrawUMLFeature {
  override protected def e: Uml#Connector
  
  import ops._
  
  def ends = e.getEnd.toSeq
  
  def _type = Option.apply( e.getType )
  
}