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

import scala.collection.immutable.Seq
import scala.{Any,Boolean,Int,StringContext}
import scala.Predef.String

trait MagicDrawUMLEnumeration 
  extends MagicDrawUMLDataType
  with UMLEnumeration[MagicDrawUML] {

  override protected def e: Uml#Enumeration
  def getMagicDrawEnumeration: Uml#Enumeration = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  
  override def ownedLiteral
  : Seq[UMLEnumerationLiteral[Uml]]
  = e.getOwnedLiteral.to[Seq]

}

case class MagicDrawUMLEnumerationImpl
(e: MagicDrawUML#Enumeration, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLEnumeration
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLEnumerationImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLEnumeration(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}