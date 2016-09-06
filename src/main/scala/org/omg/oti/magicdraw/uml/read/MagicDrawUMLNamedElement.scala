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

import org.omg.oti.uml.read.api._
import scala.{Option,None,Some}
import scala.Predef.String
import scala.collection.immutable._

trait MagicDrawUMLNamedElement 
  extends MagicDrawUMLElement
  with UMLNamedElement[MagicDrawUML] {

  override protected def e: Uml#NamedElement
  def getMagicDrawNamedElement = e
  implicit val umlOps = ops
  import umlOps._
  
  override def clientDependency: Set[UMLDependency[Uml]] =
    e.getClientDependency.to[Set]
  
  override def name: Option[String] =
    e.getName match {
      case null => None
      case "" => None
      case n => Some(n)
    }
  
  override def qualifiedName: Option[String] =
    Option.apply( e.getQualifiedName )
  
  override def visibility: Option[UMLVisibilityKind.Value] =
    Option.apply(e.getVisibility) match {
      case None =>
        None
      case Some(v) => v match {
        case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PUBLIC =>
          Some(UMLVisibilityKind.public)
        case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PRIVATE =>
          Some(UMLVisibilityKind._private)
        case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PROTECTED =>
          Some(UMLVisibilityKind._protected)
        case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PACKAGE =>
          Some(UMLVisibilityKind._package)
      }
    }
  
  override def event_durationObservation: Set[UMLDurationObservation[Uml]] =
    e.get_durationObservationOfEvent.to[Set]
  
  override def event_timeObservation: Set[UMLTimeObservation[Uml]] =
    e.get_timeObservationOfEvent.to[Set]

  override def member_memberNamespace: Set[UMLNamespace[Uml]] =
    e.get_namespaceOfMember.to[Set]

  override def signature_message: Set[UMLMessage[Uml]] =
    e.get_messageOfSignature.to[Set]

  override def message_considerIgnoreFragment: Set[UMLConsiderIgnoreFragment[Uml]] =
    e.get_considerIgnoreFragmentOfMessage.to[Set]

  /**
   * @todo move this to UMLNamedElementOps
   */
  override def inheritedMember_inheritingClassifier: Set[UMLClassifier[Uml]] =
    member_memberNamespace
    .selectByKindOf { case cls: UMLClassifier[Uml] => cls }
    .filter { case cls => cls.inheritedMember.contains( this ) }

  // MD-specific
  
  def setName( name: String ) = e.setName( name )
  
}