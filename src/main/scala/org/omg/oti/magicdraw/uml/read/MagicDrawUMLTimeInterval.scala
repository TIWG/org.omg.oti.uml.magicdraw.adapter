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
import scala.{Option,StringContext}
import scala.Predef.String

trait MagicDrawUMLTimeInterval 
  extends MagicDrawUMLInterval
  with UMLTimeInterval[MagicDrawUML] {

  override protected def e: Uml#TimeInterval
  def getMagicDrawTimeInterval = e
  override implicit val umlOps = ops
  import umlOps._

  override def max: Option[UMLTimeExpression[Uml]] =
    for { result <- Option( e.getMax ) } yield result
    
  override def min: Option[UMLTimeExpression[Uml]] =
    for { result <- Option( e.getMin ) } yield result

}

case class MagicDrawUMLTimeIntervalImpl
(e: MagicDrawUML#TimeInterval, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLTimeInterval
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLTimeInterval(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}