package org.omg.oti.magicdraw

import org.omg.oti.UMLComment

import scala.collection.JavaConversions._

trait MagicDrawUMLComment extends MagicDrawUMLElement with UMLComment[MagicDrawUML] {
  override protected def e: Uml#Comment
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def annotatedElements = e.getAnnotatedElement.toIterator
  def getCommentOwnerIndex = e.getOwner.getOwnedComment.toList.indexOf( e )  
  def body = Option.apply(e.getBody)
}