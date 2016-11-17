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
import scala.collection.Iterable

import org.omg.oti.uml.read.api._

trait MagicDrawUMLDestroyLinkAction 
  extends MagicDrawUMLWriteLinkAction
  with UMLDestroyLinkAction[MagicDrawUML] {

  override protected def e: Uml#DestroyLinkAction
  def getMagicDrawDestroyLinkAction = e
  import ops._

  override def endData: Iterable[UMLLinkEndDestructionData[Uml]] =
    umlLinkEndData( e.getEndData.toIterable ) selectByKindOf
    { case d: UMLLinkEndDestructionData[Uml] => d }

}

case class MagicDrawUMLDestroyLinkActionImpl
(e: MagicDrawUML#DestroyLinkAction, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLDestroyLinkAction