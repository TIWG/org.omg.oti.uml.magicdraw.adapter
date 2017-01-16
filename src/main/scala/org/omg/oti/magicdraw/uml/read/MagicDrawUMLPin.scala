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
import scala.{Boolean,Option,None,Some}
import scala.Predef.require

trait MagicDrawUMLPin 
  extends MagicDrawUMLObjectNode
  with MagicDrawUMLMultiplicityElement
  with UMLPin[MagicDrawUML] {

  override protected def e: Uml#Pin
  def getMagicDrawPin: Uml#Pin = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def isControl: Boolean = e.isControl

	override def upperValue
  : Option[UMLValueSpecification[Uml]]
  = ( Option.apply(e.getUpperBound), Option.apply(e.getUpperValue) ) match {
    case ( Some( v ), None ) => Some( v )
    case ( None, Some( v ) ) => Some( v )
    case ( Some( v1 ), Some( v2 ) ) => 
      require( v1 == v2, "MagicDraw ambiguity for Pin::upperValue vs. Pin::upperBound")
      Some( v1 )
    case ( None, None ) => None    
  }
}