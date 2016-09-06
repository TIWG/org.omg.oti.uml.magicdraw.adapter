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
import scala.{Option,None,Some}
import scala.collection.immutable._
import scala.collection.JavaConversions._

trait MagicDrawUMLTrigger 
  extends MagicDrawUMLNamedElement
  with UMLTrigger[MagicDrawUML] {

  override protected def e: Uml#Trigger
  def getMagicDrawTrigger = e
  override implicit val umlOps = ops
  import umlOps._

  // 13.2
  override def event: Option[UMLEvent[Uml]] =
    for { result <- Option(e.getEvent) } yield result
  
  // 13.2
  override def port: Set[UMLPort[Uml]] =
    e.getPort.to[Set]
  
  // 16.39
  override def replyToCall_replyAction: Option[UMLReplyAction[Uml]] =
    e
    .get_replyActionOfReplyToCall()
    .headOption
    .fold[Option[UMLReplyAction[Uml]]](None){ ra =>
      Some(ra)
    }

}

case class MagicDrawUMLTriggerImpl
(e: MagicDrawUML#Trigger, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLTrigger