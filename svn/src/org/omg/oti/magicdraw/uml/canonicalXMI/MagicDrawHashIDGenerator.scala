/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2016, California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * *   Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * *   Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 * *   Neither the name of Caltech nor its operating division, the Jet
 *    Propulsion Laboratory, nor the names of its contributors may be
 *    used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw.uml.canonicalXMI

import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.json.common.OTIPrimitiveTypes._
import org.omg.oti.uml.UMLError
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.xmi._

import scala.collection.Iterable
import scala.collection.immutable._
import scala.language.postfixOps
import scala.Predef.String
import scalaz.Scalaz._
import scalaz._

case class MagicDrawHashIDGenerator
()
( override implicit val documentSet: DocumentSet[MagicDrawUML] )
  extends DocumentHashIDGenerator[MagicDrawUML] {

  import umlOps._
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
  val MD_crule0: ContainedElement2UUIDRule = {
    case ( owner, ownerUUID, cf, is: MagicDrawUMLInstanceSpecification )
      if is.isMagicDrawUMLAppliedStereotypeInstance =>
      val uuid =
        OTI_UUID.unwrap(ownerUUID) +
          "_" + IDGenerator.xmlSafeID( cf.propertyName ) +
          ".appliedStereotypeInstance"
      OTI_UUID( uuid ).right
  }

  val MD_crule1a0: ContainedElement2UUIDRule = {
    case ( owner, ownerUUID, cf, ev: MagicDrawUMLElementValue ) =>
      ev.element
      .fold[Set[java.lang.Throwable] \/ (String @@ OTI_UUID)](
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
          .fold[Set[java.lang.Throwable] \/ (String @@ OTI_UUID)](
            Set(
              UMLError
              .illegalElementError[MagicDrawUML, UMLElement[MagicDrawUML]](
                "ElementValue must refer to a named NamedElement",
                Iterable(owner, ev, nev ) ) )
            .left
          ){ n =>
            OTI_UUID(
                OTI_UUID.unwrap(ownerUUID) + "_" +
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

  protected val elementRules =
    List( rule0 )

  protected val containmentRules =
    List( MD_crule0, crule1, MD_crule1a0, crule1a, crule3, crule1b, crule2, crule4, crule5, crule6 )

}