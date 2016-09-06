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

package org.omg.oti.magicdraw.uml.trees

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

import org.omg.oti.magicdraw.uml.read.{MagicDrawUML, MagicDrawUMLUtil}
import org.omg.oti.uml.read.api.UMLClassifier
import org.omg.oti.uml.trees._

import scala.{Boolean,Option,StringContext}
import scala.collection.immutable.Set
import scala.Predef.String
import scalaz._, Scalaz._

case class MagicDrawUMLTreeOps
( umlUtil: MagicDrawUMLUtil,
  blockSpecificTypeProfileName: String,
  blockSpecificTypeStereotypeName: String)
extends TreeOps[MagicDrawUML] {

  val blockSpecificTypePF
  : Set[java.lang.Throwable] \/ com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile =
    Option
    .apply(StereotypesHelper.getProfile(umlUtil.project, blockSpecificTypeProfileName))
    .fold[Set[java.lang.Throwable] \/ com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile](
      Set(
        treeOpsException(
          this,
          s"MagicDrawUMLTreeOps initialization failed: No profile named '$blockSpecificTypeProfileName'"))
      .left[com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile]
    ){ _.right }

  val blockSpecificTypeS
  : Set[java.lang.Throwable] \/ com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype =
    blockSpecificTypePF.flatMap { _blockSpecificTypePF =>
      Option
        .apply(StereotypesHelper.getStereotype(umlUtil.project, blockSpecificTypeStereotypeName, _blockSpecificTypePF))
        .fold[Set[java.lang.Throwable] \/ com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype](
        Set(
          treeOpsException(
            this,
            s"MagicDrawUMLTreeOps initialization failed: No stereotype named '$blockSpecificTypeStereotypeName'"))
          .left[com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype]
      ) {
        _.right
      }
    }

  val sysmlPF
  : Set[java.lang.Throwable] \/ com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile =
    Option
    .apply(StereotypesHelper.getProfile(umlUtil.project, "SysML"))
    .fold[Set[java.lang.Throwable] \/ com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile](
      Set(
        treeOpsException(
          this,
          "MagicDrawUMLTreeOps initialization failed: No profile named 'SysML'"))
      .left[com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile]
    ){ _.right }


  val sysmlPropertySpecificTypeS
  : Set[java.lang.Throwable] \/ com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype =
    sysmlPF.flatMap { _sysmlPF =>
      Option
        .apply(StereotypesHelper.getStereotype(umlUtil.project, "PropertySpecificType", _sysmlPF))
        .fold[Set[java.lang.Throwable] \/ com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype](
        Set(
          treeOpsException(
            this,
            "MagicDrawUMLTreeOps initialization failed: No stereotype named 'PropertySpecificType'"))
          .left[com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype]
      ) {
        _.right
      }
    }


  def isRootBlockSpecificType(treeType: UMLClassifier[MagicDrawUML])
  : Set[java.lang.Throwable] \/ Boolean =
    blockSpecificTypeS.map { _blockSpecificTypeS =>
      StereotypesHelper.hasStereotype(
        umlUtil.umlMagicDrawUMLClassifier(treeType).getMagicDrawClassifier,
        _blockSpecificTypeS)
    }

  def isPartPropertySpecificType(treeType: UMLClassifier[MagicDrawUML])
  : Set[java.lang.Throwable] \/ Boolean =
    sysmlPropertySpecificTypeS.map { _sysmlPropertySpecificTypeS =>
      StereotypesHelper.hasStereotype(
        umlUtil.umlMagicDrawUMLClassifier(treeType).getMagicDrawClassifier,
        _sysmlPropertySpecificTypeS)
    }
  
}