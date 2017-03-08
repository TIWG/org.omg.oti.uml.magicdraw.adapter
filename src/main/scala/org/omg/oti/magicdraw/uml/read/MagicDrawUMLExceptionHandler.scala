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
import scala.{Any,Boolean,Int,Option,StringContext}
import scala.Predef.String
import scala.collection.immutable._

trait MagicDrawUMLExceptionHandler 
  extends MagicDrawUMLElement
  with UMLExceptionHandler[MagicDrawUML] {

  override protected def e: Uml#ExceptionHandler
  def getMagicDrawExceptionHandler: Uml#ExceptionHandler = e
  implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def exceptionInput
  : Option[UMLObjectNode[Uml]]
  = for { result <- Option( e.getExceptionInput ) } yield result
  
	override def exceptionType
  : Set[UMLClassifier[Uml]]
  = e.getExceptionType.to[Set]
  
	override def handlerBody
  : Option[UMLExecutableNode[Uml]]
  = for { result <- Option( e.getHandlerBody ) } yield result
  
	override def protectedNode
  : Option[UMLExecutableNode[Uml]]
  = for { result <- Option( e.getProtectedNode ) } yield result

}

case class MagicDrawUMLExceptionHandlerImpl
(e: MagicDrawUML#ExceptionHandler, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLExceptionHandler
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLExceptionHandlerImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLExceptionHandler(ID=${e.getID})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}