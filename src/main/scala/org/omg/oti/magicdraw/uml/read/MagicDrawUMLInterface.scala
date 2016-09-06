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
import scala.StringContext
import scala.Predef.{???,String}

import org.omg.oti.uml.read.api._

trait MagicDrawUMLInterface 
  extends MagicDrawUMLClassifier
  with UMLInterface[MagicDrawUML] {

  override protected def e: Uml#Interface
  def getMagicDrawInterface = e
  override implicit val umlOps = ops
  import umlOps._

	override def nestedClassifier: Seq[UMLClassifier[Uml]] =
    e.getNestedClassifier.to[Seq]
    
  override def ownedAttribute: Seq[UMLProperty[Uml]] =
    e.getOwnedAttribute.to[Seq]
    
  override def ownedOperation: Seq[UMLOperation[Uml]] =
    e.getOwnedOperation.to[Seq]
    

  override def provided_port: Set[UMLPort[Uml]] = ???
  
  override def required_port: Set[UMLPort[Uml]] = ???

  override def required_component: Set[UMLComponent[Uml]] = ???
  
  override def provided_component: Set[UMLComponent[Uml]] = ???
    
  override def contract_interfaceRealization: Set[UMLInterfaceRealization[Uml]] =
    umlInterfaceRealization( e.get_interfaceRealizationOfContract.toSet )
   
  override def redefinedInterface: Set[UMLInterface[Uml]] =
    umlInterface( e.getRedefinedInterface.toSet )

  override def redefinedInterface_interface: Set[UMLInterface[Uml]] =
    umlInterface( e.get_interfaceOfRedefinedInterface.toSet )

}

case class MagicDrawUMLInterfaceImpl
(e: MagicDrawUML#Interface, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLInterface
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLInterface(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}