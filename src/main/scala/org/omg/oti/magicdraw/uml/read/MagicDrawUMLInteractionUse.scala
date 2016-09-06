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

import scala.collection.JavaConversions._
import scala.collection.immutable._

import org.omg.oti.uml.read.api._
import scala.Option

trait MagicDrawUMLInteractionUse 
  extends MagicDrawUMLInteractionFragment
  with UMLInteractionUse[MagicDrawUML] {

  override protected def e: Uml#InteractionUse
  def getMagicDrawInteractionUse = e
  override implicit val umlOps = ops
  import umlOps._

  // 17.18
  override def argument: Seq[UMLValueSpecification[Uml]] =
    e.getArgument.to[Seq]
  
  // 17.18
  override def refersTo: Option[UMLInteraction[Uml]] =
    for { result <- Option(e.getRefersTo) } yield result
  
  // 17.18
  override def returnValue: Option[UMLValueSpecification[Uml]] =
    for { result <- Option(e.getReturnValue) } yield result
  
  // BUG: NO FIGURE!
  override def returnValueRecipient: Option[UMLProperty[Uml]] =
    for { result <- Option(e.getReturnValueRecipient) } yield result

}

case class MagicDrawUMLInteractionUseImpl
(e: MagicDrawUML#InteractionUse, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLInteractionUse