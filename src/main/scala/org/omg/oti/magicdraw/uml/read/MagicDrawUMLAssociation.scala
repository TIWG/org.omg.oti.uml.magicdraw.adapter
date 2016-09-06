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

import scala.{Option,None,Some,StringContext}
import scala.Predef.{require,String}
import scala.collection.JavaConversions._
import scala.collection.immutable._

trait MagicDrawUMLAssociation 
  extends MagicDrawUMLClassifier
  with MagicDrawUMLRelationship
  with UMLAssociation[MagicDrawUML] {
  
  override protected def e: Uml#Association
  def getMagicDrawAssociation = e

  override implicit val umlOps = ops
  import umlOps._
  
  override def isDerived = e.isDerived
  
  override def ownedEnd = e.getOwnedEnd.toIterable
  
  override def navigableOwnedEnd = e.getNavigableOwnedEnd.to[Set]
  
  override def memberEnd = e.getMemberEnd.to[Seq]
  
  override def type_connector = e.get_connectorOfType.to[Set]
    
  override def association_clearAssociationAction: Option[UMLClearAssociationAction[Uml]] = {
    val actions = e.get_clearAssociationActionOfAssociation
    require(actions.size <= 1)
    if (actions.isEmpty) None
    else Some( actions.iterator.next )
  }

}

case class MagicDrawUMLAssociationImpl
(e: MagicDrawUML#Association, ops: MagicDrawUMLUtil)
extends MagicDrawUMLAssociation
with sext.PrettyPrinting.TreeString
with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLAssociation(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}