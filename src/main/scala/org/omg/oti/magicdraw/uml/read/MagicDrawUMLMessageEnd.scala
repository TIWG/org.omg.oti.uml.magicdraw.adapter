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
import scala.Option

trait MagicDrawUMLMessageEnd 
  extends MagicDrawUMLNamedElement
  with UMLMessageEnd[MagicDrawUML] {

  override protected def e: Uml#MessageEnd
  def getMagicDrawMessageEnd: Uml#MessageEnd = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  // 17.7
  override def message
  : Option[UMLMessage[Uml]]
  = for { result <- Option(e.getMessage) } yield result
  
  // 17.7
  override def receiveEvent_endMessage
  : Option[UMLMessage[Uml]]
  = for { result <- Option(e.get_messageOfReceiveEvent()) } yield result
  
  // 17.7
  override def sendEvent_endMessage
  : Option[UMLMessage[Uml]]
  = for { result <- Option(e.get_messageOfSendEvent()) } yield result
}