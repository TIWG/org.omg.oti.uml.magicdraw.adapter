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

package org.omg.oti.magicdraw.uml.canonicalXMI

import scala.Predef.String
import scala.collection.immutable._
import scala.collection.Iterable

import org.omg.oti.json.common.OTIPrimitiveTypes._
import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.uml.xmi._

import org.omg.oti.magicdraw.uml.read._
import scalaz._, Scalaz._

case class MagicDrawIDGenerator
()
( override implicit val documentSet: DocumentSet[MagicDrawUML] )
  extends DocumentIDGenerator[MagicDrawUML] {

  implicit val idg = this

  val element2id = scala.collection.mutable.HashMap[
    UMLElement[MagicDrawUML],
    \/[Set[java.lang.Throwable], (String @@ OTI_ID)]]()

  val element2uuid = scala.collection.mutable.HashMap[
    UMLElement[MagicDrawUML],
    \/[Set[java.lang.Throwable], (String @@ OTI_UUID)]]()

  override def getMappedOrReferencedElement( ref: UMLElement[MagicDrawUML] )
  : Set[java.lang.Throwable] \/ UMLElement[MagicDrawUML] =
    ref
    .xmiID()
    .map(OTI_ID.unwrap)
    .map {
      // @todo needs update
      //case "_UML_" => MDBuiltInUML.scope
      //case "_StandardProfile_" => MDBuiltInStandardProfile.scope
      case _ => ref
    }
  
  // -------------
  val MD_crule0: MagicDrawIDGenerator.this.umlOps.ContainedElement2IDRule = {
    case ( owner, ownerID, cf, is: MagicDrawUMLInstanceSpecification )
      if is.isMagicDrawUMLAppliedStereotypeInstance =>
      val id = OTI_ID.unwrap(ownerID) + "_" + IDGenerator.xmlSafeID( cf.propertyName ) + ".appliedStereotypeInstance"
      OTI_ID( id ).right
  }

  val MD_crule1a0: MagicDrawIDGenerator.this.umlOps.ContainedElement2IDRule = {
    case ( owner, ownerID, cf, ev: MagicDrawUMLElementValue ) =>
      ev.element
      .fold[Set[java.lang.Throwable] \/ (String @@ OTI_ID)](
          Set(
            UMLError
            .illegalElementError[MagicDrawUML, UMLElement[MagicDrawUML]](
              "ElementValue without Element is not supported",
              Iterable(owner, ev) ) )
          .left
      ){
        case nev: UMLNamedElement[MagicDrawUML] =>
          nev
          .name
          .fold[Set[java.lang.Throwable] \/ (String @@ OTI_ID)](
            Set(
              UMLError
              .illegalElementError[MagicDrawUML, UMLElement[MagicDrawUML]](
                "ElementValue must refer to a named NamedElement",
                Iterable(owner, ev, nev ) ) )
            .left
          ){ n =>
            OTI_ID(
                OTI_ID.unwrap(ownerID) + "_" +
                IDGenerator.xmlSafeID( cf.propertyName + "." + n ) )
            .right
          }
        case uev: UMLElement[MagicDrawUML] =>
          Set(
            UMLError
            .illegalElementError[MagicDrawUML, UMLElement[MagicDrawUML]](
              "ElementValue refers to an Element that is not a NamedElement!",
              Iterable(owner, ev, uev ) ) )
          .left
      }
  }

  protected val elementRules
  : List[MagicDrawIDGenerator.this.umlOps.Element2IDRule]
  = List( rule0 )

  protected val containmentRules
  : List[MagicDrawIDGenerator.this.umlOps.ContainedElement2IDRule]
  = List( MD_crule0, crule1, MD_crule1a0, crule1a, crule3, crule1b, crule2, crule4, crule5, crule6 )

}