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
package org.omg.oti.magicdraw.uml.read

import org.omg.oti.uml.read.api._

import scala.{Option,None,Some}
import scala.Predef.String

trait MagicDrawUMLElementImport 
  extends UMLElementImport[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#ElementImport
  def getMagicDrawElementImport = e

  override implicit val umlOps = ops
  import umlOps._

  override def alias: Option[String] =
    e.getAlias match {
    case null => None
    case "" => None
    case s => Some( s )
  }

  override def metaclassReference_profile: Option[UMLProfile[Uml]] =
    Option.apply( e.get_profileOfMetaclassReference )
    
  override def visibility: Option[UMLVisibilityKind.Value] =
    Option.apply(e.getVisibility)
    .fold[Option[UMLVisibilityKind.Value]](None) {
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

case class MagicDrawUMLElementImportImpl(val e: MagicDrawUML#ElementImport, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLElementImport