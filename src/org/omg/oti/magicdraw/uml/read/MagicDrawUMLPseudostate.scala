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
import scala.collection.JavaConversions._
import scala.{Option,Some}

trait MagicDrawUMLPseudostate 
  extends UMLPseudostate[MagicDrawUML]
  with MagicDrawUMLVertex {

  override protected def e: Uml#Pseudostate
  def getMagicDrawPseudostate = e
  override implicit val umlOps = ops
  import umlOps._

  // 14.1
  override def kind
  : Option[UMLPseudostateKind.Value] =
  Option.apply(e.getKind)
  .fold[Option[UMLPseudostateKind.Value]](Option.empty[UMLPseudostateKind.Value]){
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.INITIAL =>
      Some(UMLPseudostateKind.initial)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.DEEPHISTORY =>
      Some(UMLPseudostateKind.deepHistory)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.SHALLOWHISTORY =>
      Some(UMLPseudostateKind.shallowHistory)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.JOIN =>
      Some(UMLPseudostateKind.join)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.FORK =>
      Some(UMLPseudostateKind.fork)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.JUNCTION =>
      Some(UMLPseudostateKind.junction)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.CHOICE =>
      Some(UMLPseudostateKind.choice)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.ENTRYPOINT =>
      Some(UMLPseudostateKind.entryPoint)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.EXITPOINT =>
      Some(UMLPseudostateKind.exitPoint)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.TERMINATE =>
      Some(UMLPseudostateKind.terminate)
  }
  
  // 14.1
  override def entry_connectionPointReference
  : Option[UMLConnectionPointReference[Uml]] =
  Option.apply(e.get_connectionPointReferenceOfEntry.toList.headOption.getOrElse(null))
  
  // 14.1
  override def exit_connectionPointReference
  : Option[UMLConnectionPointReference[Uml]] =
  Option.apply(e.get_connectionPointReferenceOfExit.toList.headOption.getOrElse(null))

}

case class MagicDrawUMLPseudostateImpl(val e: MagicDrawUML#Pseudostate, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLPseudostate