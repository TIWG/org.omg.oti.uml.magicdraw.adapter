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

import scala.{Any,Boolean,Int,Option,None,Some,StringContext}
import scala.Predef.String

trait MagicDrawUMLElementImport 
  extends MagicDrawUMLDirectedRelationship
  with UMLElementImport[MagicDrawUML] {

  override protected def e: Uml#ElementImport
  def getMagicDrawElementImport: Uml#ElementImport = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def alias
  : Option[String]
  = e.getAlias match {
    case null => None
    case "" => None
    case s => Some( s )
  }

  override def metaclassReference_profile
  : Option[UMLProfile[Uml]]
  = for { result <- Option.apply( e.get_profileOfMetaclassReference ) } yield result
    
  override def visibility
  : Option[UMLVisibilityKind.Value]
  = Option.apply(e.getVisibility)
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

case class MagicDrawUMLElementImportImpl
(e: MagicDrawUML#ElementImport, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLElementImport
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLElementImportImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLElementImport(ID=${e.getID})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}