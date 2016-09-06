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

import scala.collection.immutable._
import scala.Option
import scala.Predef.???

import org.omg.oti.uml.read.api._

trait MagicDrawUMLMessage 
  extends MagicDrawUMLNamedElement
  with UMLMessage[MagicDrawUML] {

  override protected def e: Uml#Message
  def getMagicDrawMessage = e

  // 17.7
  override def argument: Seq[UMLValueSpecification[Uml]] = ???
  
  // 17.7
  override def connector: Option[UMLConnector[Uml]] = ???
  
  // 17.7
  override def messageKind: Option[UMLMessageKind.Value] = ???
  
  // 17.7
  override def messageSort: Option[UMLMessageSort.Value] = ???
  
  // 17.7
  override def receiveEvent: Option[UMLMessageEnd[Uml]] = ???
  
  // 17.7
  override def sendEvent: Option[UMLMessageEnd[Uml]] = ???
  
  // 17.7
  override def signature: Option[UMLNamedElement[Uml]] = ???
  
  // 17.7
  override def message_messageEnd: Set[UMLMessageEnd[Uml]] = ???
  
  // 20.1
  override def realizingMessage_informationFlow: Set[UMLInformationFlow[Uml]] = ???

}

case class MagicDrawUMLMessageImpl
(e: MagicDrawUML#Message, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLMessage