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
package org.omg.oti.magicdraw.uml.read

import scala.collection.JavaConversions._
import org.omg.oti.uml.read.api._
import scala.collection.immutable._
import scala.StringContext
import scala.Predef.String

trait MagicDrawUMLInformationFlow 
  extends MagicDrawUMLPackageableElement
  with MagicDrawUMLDirectedRelationship
  with UMLInformationFlow[MagicDrawUML] {

  override protected def e: Uml#InformationFlow
  def getMagicDrawInformationFlow = e

  override implicit val umlOps = ops
  import umlOps._
  
  // 20.1  
	override def conveyed: Set[UMLClassifier[Uml]] = e.getConveyed.to[Set]
    
  // 20.1  
	override def realization: Set[UMLRelationship[Uml]] = e.getRealization.to[Set]
  
  // 20.1  
	override def realizingActivityEdge: Set[UMLActivityEdge[Uml]] = e.getRealizingActivityEdge.to[Set]
	
  // 20.1
  override def realizingConnector: Set[UMLConnector[Uml]] = e.getRealizingConnector.to[Set]
	
  // 20.1  
  override def realizingMessage: Set[UMLMessage[Uml]] = e.getRealizingMessage.to[Set]

}

case class MagicDrawUMLInformationFlowImpl
(e: MagicDrawUML#InformationFlow, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLInformationFlow
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLInformationFlow(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}