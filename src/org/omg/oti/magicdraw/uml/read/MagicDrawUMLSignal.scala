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

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.StringContext
import scala.Predef.String

import org.omg.oti.uml.read.api._

trait MagicDrawUMLSignal 
  extends UMLSignal[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#Signal
  def getMagicDrawSignal = e
  override implicit val umlOps = ops
  import umlOps._

  // 10.5
  override def ownedAttribute: Seq[UMLProperty[Uml]] =
    e.getOwnedAttribute().to[Seq]
  
  // 10.5
  override def signal_reception: Set[UMLReception[Uml]] =
    e.get_receptionOfSignal().toSet[Uml#Reception]
  
  // 16.13
  override def signal_broadcastSignalAction: Set[UMLBroadcastSignalAction[Uml]] =
    e.get_broadcastSignalActionOfSignal().toSet[Uml#BroadcastSignalAction]
  
  // 16.13
  override def signal_sendSignalAction: Set[UMLSendSignalAction[Uml]] =
    e.get_sendSignalActionOfSignal().toSet[Uml#SendSignalAction]
  
  // 13.2
  override def signal_signalEvent: Set[UMLSignalEvent[Uml]] =
    e.get_signalEventOfSignal().toSet[Uml#SignalEvent]

}

case class MagicDrawUMLSignalImpl(val e: MagicDrawUML#Signal, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLSignal
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLSignal(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}
