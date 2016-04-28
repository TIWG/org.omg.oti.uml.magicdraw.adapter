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
import scala.collection.immutable._
import scala.{Boolean,Option}

import org.omg.oti.uml.read.api._

trait MagicDrawUMLLoopNode 
  extends MagicDrawUMLStructuredActivityNode
  with UMLLoopNode[MagicDrawUML] {

  override protected def e: Uml#LoopNode
  def getMagicDrawLoopNode = e

  override implicit val umlOps = ops
  import umlOps._

  // 16.45
  override def bodyOutput: Seq[UMLOutputPin[Uml]] =
    e.getBodyOutput().to[Seq]

  // 16.45
  override def bodyPart: Set[UMLExecutableNode[Uml]] =
    e.getBodyPart().to[Set]

  // 16.45
  override def decider: Option[UMLOutputPin[Uml]] =
    for { result <- Option(e.getDecider()) } yield result

  // 16.45
  override def isTestedFirst: Boolean =
    e.isTestedFirst()

  // 16.45
  override def loopVariable: Seq[UMLOutputPin[Uml]] =
    e.getLoopVariable().to[Seq]

  // 16.45
  override def loopVariableInput: Seq[UMLInputPin[Uml]] =
    e.getLoopVariableInput().to[Seq]

  // 16.45
  override def result: Seq[UMLOutputPin[Uml]] =
    e.getResult().to[Seq]

  // 16.45
  override def setupPart: Set[UMLExecutableNode[Uml]] =
    e.getSetupPart().to[Set]

  // 16.45
  override def test: Set[UMLExecutableNode[Uml]] =
    e.getTest().to[Set]

}

case class MagicDrawUMLLoopNodeImpl
(e: MagicDrawUML#LoopNode, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLLoopNode