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
import scala.Option

trait MagicDrawUMLReplyAction 
  extends MagicDrawUMLAction
  with UMLReplyAction[MagicDrawUML] {

  override protected def e: Uml#ReplyAction
  def getMagicDrawReplyAction = e
  override implicit val umlOps = ops
  import umlOps._

  // 16.39
  override def replyToCall: Option[UMLTrigger[Uml]] =
    for { result <- Option(e.getReplyToCall) } yield result
  
  // 16.39
  override def replyValue: Seq[UMLInputPin[Uml]] =
    e.getReplyValue.to[Seq]
  
  // 16.39
  override def returnInformation: Option[UMLInputPin[Uml]] =
    for { result <- Option(e.getReturnInformation) } yield result

}

case class MagicDrawUMLReplyActionImpl
(e: MagicDrawUML#ReplyAction, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLReplyAction