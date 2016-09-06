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

trait MagicDrawUMLSendObjectAction 
  extends MagicDrawUMLInvocationAction
  with UMLSendObjectAction[MagicDrawUML] {

  override protected def e: Uml#SendObjectAction
  def getMagicDrawSendObjectAction = e
  override implicit val umlOps = ops
  import umlOps._

  // 16.13
	override def target: Option[UMLInputPin[Uml]] =
    for { result <- Option(e.getTarget) } yield result

}

case class MagicDrawUMLSendObjectActionImpl
(e: MagicDrawUML#SendObjectAction, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLSendObjectAction