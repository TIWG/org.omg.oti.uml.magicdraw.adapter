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
package org.omg.oti.magicdraw.uml.trees

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

import org.omg.oti.uml.UMLError
import org.omg.oti.magicdraw.uml.read.{MagicDrawUML, MagicDrawUMLUtil}
import org.omg.oti.uml.read.api.UMLClassifier
import org.omg.oti.uml.trees._

import scala.{Boolean,Option,None,Some,StringContext}
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