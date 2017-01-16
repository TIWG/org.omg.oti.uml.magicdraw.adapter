/*
 * Copyright 2014 California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * License Terms
 */

package org.omg.oti.magicdraw.uml.read

import org.omg.oti.uml.read.api._

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.{Any,Boolean,Int,Option,None,Some,StringContext}
import scala.Predef.String

import org.omg.oti.uml.read.api._

trait MagicDrawUMLMessage 
  extends MagicDrawUMLNamedElement
  with UMLMessage[MagicDrawUML] {

  override protected def e: Uml#Message
  def getMagicDrawMessage: Uml#Message = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  // 17.7
  override def argument
  : Seq[UMLValueSpecification[Uml]]
  = e.getArgument.to[Seq]
  
  // 17.7
  override def connector
  : Option[UMLConnector[Uml]]
  = for { result <- Option( e.getConnector ) } yield result
  
  // 17.7
  override def messageKind
  : Option[UMLMessageKind.Value]
  = Option.apply(e.getMessageKind).fold[Option[UMLMessageKind.Value]](None) {
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageKindEnum.COMPLETE =>
      Some(UMLMessageKind.complete)
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageKindEnum.FOUND =>
      Some(UMLMessageKind.found)
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageKindEnum.LOST =>
      Some(UMLMessageKind.lost)
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageKindEnum.UNKNOWN =>
      Some(UMLMessageKind.unknown)
  }

  // 17.7
  override def messageSort
  : Option[UMLMessageSort.Value]
  = Option.apply(e.getMessageSort).fold[Option[UMLMessageSort.Value]](None) {
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.ASYNCHCALL =>
      Some(UMLMessageSort.asynchCall)
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.ASYNCHSIGNAL =>
      Some(UMLMessageSort.asynchSignal)
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.CREATEMESSAGE =>
      Some(UMLMessageSort.createMessage)
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.DELETEMESSAGE =>
      Some(UMLMessageSort.deleteMessage)
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.REPLY =>
      Some(UMLMessageSort.reply)
    case com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.SYNCHCALL =>
      Some(UMLMessageSort.synchCall)
  }
  
  // 17.7
  override def receiveEvent
  : Option[UMLMessageEnd[Uml]]
  = for { result <- Option( e.getReceiveEvent ) } yield result
  
  // 17.7
  override def sendEvent
  : Option[UMLMessageEnd[Uml]]
  = for { result <- Option( e.getSendEvent ) } yield result
  
  // 17.7
  override def signature
  : Option[UMLNamedElement[Uml]]
  = for { result <- Option( e.getSignature ) } yield result
  
  // 17.7
  override def message_messageEnd
  : Set[UMLMessageEnd[Uml]]
  = e.get_messageEndOfMessage().to[Set]
  
  // 20.1
  override def realizingMessage_informationFlow
  : Set[UMLInformationFlow[Uml]]
  = e.get_informationFlowOfRealizingMessage().to[Set]

}

case class MagicDrawUMLMessageImpl
(e: MagicDrawUML#Message, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLMessage
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLMessageImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLMessage(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}