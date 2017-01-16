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
import scala.{Option,Some}

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read.api._

trait MagicDrawUMLPackageableElement 
  extends MagicDrawUMLNamedElement
  with MagicDrawUMLParameterableElement
  with UMLPackageableElement[MagicDrawUML] {

  override protected def e: Uml#PackageableElement
  def getMagicDrawPackageableElement: Uml#PackageableElement = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def visibility
  : Option[UMLVisibilityKind.Value]
  = Option.apply(e.getVisibility)
    .fold[Option[UMLVisibilityKind.Value]] {
        Some(UMLVisibilityKind.public)
     }{
        case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PUBLIC =>
          Some(UMLVisibilityKind.public)
        case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PRIVATE =>
          Some(UMLVisibilityKind._private)
        case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PROTECTED =>
          Some(UMLVisibilityKind._protected)
        case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PACKAGE =>
          Some(UMLVisibilityKind._package)
      }

  override def importedMember_namespace
  : Set[UMLNamespace[Uml]]
  = throw UMLError.umlAdaptationError("MagicDrawUMLNamespace.importedMember_namespace not available")

  override def deployedElement_deploymentTarget
  : Set[UMLDeploymentTarget[Uml]]
  = throw UMLError.umlAdaptationError("MagicDrawUMLNamespace.deployedElement_deploymentTarget not available")

  override def utilizedElement_manifestation
  : Set[UMLManifestation[Uml]]
  = e.get_manifestationOfUtilizedElement.to[Set]
  
}