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
import scala.{Any,Boolean,Int,Option,None,Some,StringContext}
import scala.Predef.String

import org.omg.oti.uml.read.api._

trait MagicDrawUMLPackage 
  extends MagicDrawUMLPackageableElement
  with MagicDrawUMLNamespace
  with MagicDrawUMLTemplateableElement
  with UMLPackage[MagicDrawUML] {

  override protected def e: Uml#Package
  def getMagicDrawPackage: Uml#Package = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  def URI
  : Option[String]
  = e.getURI match {
      case null => None
      case ""   => None
      case uri  => Some( uri )
    }

  override def packagedElement
  : Set[UMLPackageableElement[Uml]]
  = e.getPackagedElement.to[Set]
  
  override def nestingPackage
  : Option[UMLPackage[Uml]]
  = for { result <- Option(e.getNestingPackage) } yield result

}

case class MagicDrawUMLPackageImpl
(e: MagicDrawUML#Package, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLPackage
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLPackageImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLPackage(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}