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

import scala.{Option,StringContext}
import scala.Predef.String
import scala.collection.JavaConversions._
import scala.collection.immutable._

import org.omg.oti.uml.read.api._

trait MagicDrawUMLExpression 
  extends MagicDrawUMLValueSpecification
  with UMLExpression[MagicDrawUML] {

  override protected def e: Uml#Expression
  def getMagicDrawExpression = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  //import ops._
  
  def symbol = Option.apply( e.getSymbol )
  def operand = e.getOperand.to[Seq]

}

case class MagicDrawUMLExpressionImpl
(e: MagicDrawUML#Expression, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLExpression
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLExpression(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}