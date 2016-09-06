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

package org.omg.oti.magicdraw.uml.characteristics

import org.omg.oti.magicdraw.uml.read.{MagicDrawUML, MagicDrawUMLUtil}
import org.omg.oti.json.common.OTIDocumentSetConfiguration
import org.omg.oti.uml.characteristics.OTICharacteristicsDataProvider
import org.omg.oti.uml.read.api.UMLPackage
import org.omg.oti.uml.read.operations.UMLOps

import scala.collection.immutable.Vector
import scala.reflect.runtime.universe._

case class MagicDrawOTICharacteristicsDataProvider
(override val data: Vector[OTIDocumentSetConfiguration])
(override val umlOps: MagicDrawUMLUtil)
(override implicit val umlTag: TypeTag[MagicDrawUML],
 override implicit val umlPackageTag: TypeTag[UMLPackage[MagicDrawUML]],
 override implicit val opsTag: TypeTag[UMLOps[MagicDrawUML]])
extends OTICharacteristicsDataProvider[MagicDrawUML]