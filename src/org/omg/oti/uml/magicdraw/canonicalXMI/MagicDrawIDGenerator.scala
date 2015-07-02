/*
 *
 *  License Terms
 *
 *  Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
 *  U.S. Government sponsorship acknowledged.
 *
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *
 *   *   Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *   *   Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the
 *       distribution.
 *
 *   *   Neither the name of Caltech nor its operating division, the Jet
 *       Propulsion Laboratory, nor the names of its contributors may be
 *       used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *  PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.uml.magicdraw.canonicalXMI

import java.net.URL
import java.net.MalformedURLException

import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import org.omg.oti.uml.read.api._
import org.omg.oti.uml.canonicalXMI._

import org.omg.oti.uml.magicdraw._

case class MagicDrawIDGenerator(
  val resolvedDocumentSet: ResolvedDocumentSet[MagicDrawUML] )(
    implicit val umlOps: MagicDrawUMLUtil )
  extends IDGenerator[MagicDrawUML] {

  import umlOps._

  val element2id = scala.collection.mutable.HashMap[UMLElement[Uml], Try[String]]()

  override def builtInID( self: UMLElement[Uml] ): Try[String] =
    Success( umlMagicDrawUMLElement(self).getMagicDrawElement.getID )

  override def getMappedOrReferencedElement( ref: UMLElement[Uml] ): UMLElement[Uml] =
    ref.id match {
      case "_UML_" => MDBuiltInUML.scope
      case "_StandardProfile_" => MDBuiltInStandardProfile.scope
      case _ => ref
  }
  
  // -------------
  val MD_crule0: ContainedElement2IDRule = {
    case ( owner, ownerID, cf, is: MagicDrawUMLInstanceSpecification ) if is.isMagicDrawUMLAppliedStereotypeInstance =>
      Success( ownerID + "_" + IDGenerator.xmlSafeID( cf.propertyName ) + ".appliedStereotypeInstance" )
  }

  val MD_crule1a0: ContainedElement2IDRule = {
    case ( owner, ownerID, cf, ev: MagicDrawUMLElementValue ) =>
      ev.element match {
        case None => Failure( illegalElementException( "ElementValue without Element is not supported", ev ) )
        case Some( nev: UMLNamedElement[Uml] ) =>
          nev.name match {
            case None      => Failure( illegalElementException( "ElementValue must refer to a named NamedElement", ev ) )
            case Some( n ) => Success( ownerID + "_" + IDGenerator.xmlSafeID( cf.propertyName + "." + n ) )
          }
        case Some( ev: UMLElement[Uml] ) =>
          Failure( illegalElementException( "ElementValue refers to an Element that is not a NamedElement!", ev ) )
      }
  }

  protected val elementRules = List( rule0 )
  protected val containmentRules = List( MD_crule0, crule1, MD_crule1a0, crule1a, crule3, crule1b, crule2, crule4, crule5, crule6 )

}