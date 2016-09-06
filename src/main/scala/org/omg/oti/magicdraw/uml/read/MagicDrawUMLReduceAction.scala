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
import scala.{Boolean,Option}

trait MagicDrawUMLReduceAction 
  extends MagicDrawUMLAction
  with UMLReduceAction[MagicDrawUML] {

  override protected def e: Uml#ReduceAction
  def getMagicDrawReduceAction = e
  override implicit val umlOps = ops
  import umlOps._

  // 16.56
  override def isOrdered: Boolean =
    e.isOrdered
  
  // 16.56
  override def reducer: Option[UMLBehavior[Uml]] =
    for { result <- Option(e.getReducer) } yield result

}

case class MagicDrawUMLReduceActionImpl
(e: MagicDrawUML#ReduceAction, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLReduceAction