package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._
import scala.language.postfixOps

trait MagicDrawUMLOperation extends UMLOperation[MagicDrawUML] with MagicDrawUMLBehavioralFeature {
  override protected def e: Uml#Operation
  
  import ops._
  
  override def isQuery = e.isQuery()
  
  def preCondition = e.getPrecondition.toIterable
  def postCondition = e.getPostcondition.toIterable
  def bodyCondition = Option.apply( e.getBodyCondition )
}