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
import scala.{Option,StringContext}
import scala.Predef.String

import org.omg.oti.uml.read.api._

trait MagicDrawUMLConnector 
  extends MagicDrawUMLFeature
  with UMLConnector[MagicDrawUML] {

  override protected def e: Uml#Connector
  def getMagicDrawConnector = e
  override implicit val umlOps = ops
  import umlOps._
  
  override def end: Seq[UMLConnectorEnd[Uml]] =
    e.getEnd.to[Seq]
  
  override def _type: Option[UMLAssociation[Uml]] =
    for { result <- Option.apply( e.getType ) } yield result
  
  override def connector_message: Set[UMLMessage[Uml]] =
    e.get_messageOfConnector.to[Set]
  
  override def contract: Set[UMLBehavior[Uml]] =
    e.getContract.to[Set]
  
  override def realizingConnector_informationFlow: Set[UMLInformationFlow[Uml]] =
    e.get_informationFlowOfRealizingConnector.to[Set]

}

case class MagicDrawUMLConnectorImpl
(e: MagicDrawUML#Connector, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLConnector
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLConnector(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}