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
import scala.{Any,Boolean,Int,Option,StringContext}
import scala.Predef.String

trait MagicDrawUMLSendSignalAction 
  extends MagicDrawUMLInvocationAction
  with UMLSendSignalAction[MagicDrawUML] {

  override protected def e: Uml#SendSignalAction
  def getMagicDrawSendSignalAction: Uml#SendSignalAction = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  // 16.13
	override def signal
  : Option[UMLSignal[Uml]]
  = for { result <- Option(e.getSignal) } yield result
  
  // 16.13
	override def target
  : Option[UMLInputPin[Uml]]
  = for { result <- Option(e.getTarget) } yield result

}

case class MagicDrawUMLSendSignalActionImpl
(e: MagicDrawUML#SendSignalAction, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLSendSignalAction
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLSendSignalActionImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLSendSignalAction(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}