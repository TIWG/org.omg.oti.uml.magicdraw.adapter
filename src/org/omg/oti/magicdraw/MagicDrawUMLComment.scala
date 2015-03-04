package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLComment 
  extends UMLComment[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#Comment
  import ops._

  
  def annotatedElement = e.getAnnotatedElement.toSet[com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element]
  def getCommentOwnerIndex = e.getOwner.getOwnedComment.toList.indexOf( e )  
  def body = Option.apply(e.getBody)
}