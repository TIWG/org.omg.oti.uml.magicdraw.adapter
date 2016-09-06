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
import scala.Predef.String
import scala.{Boolean, Option,None,Some,StringContext}

trait MagicDrawUMLTimeConstraint 
  extends MagicDrawUMLIntervalConstraint
  with UMLTimeConstraint[MagicDrawUML] {

  override protected def e: Uml#TimeConstraint
  def getMagicDrawTimeConstraint = e
  override implicit val umlOps = ops

  override def firstEvent: Option[Boolean] =
    if (e.isFirstEvent) None
    else Some( false )
    
  abstract override def specification: Option[UMLTimeInterval[Uml]] =
    super.specification

}

case class MagicDrawUMLTimeConstraintImpl
(e: MagicDrawUML#TimeConstraint, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLTimeConstraint
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLTimeConstraint(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}