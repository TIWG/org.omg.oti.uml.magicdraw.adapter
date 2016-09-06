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
import scala.{Option,None,Some}
import scala.Predef.String

trait MagicDrawUMLImage 
  extends MagicDrawUMLElement
  with UMLImage[MagicDrawUML] {

  override protected def e: Uml#Image
  def getMagicDrawImage = e
  
  def content: Option[String] = e.getContent  match {
    case null => None
    case "" => None
    case s => Some( s )
  }
  
  def format: Option[String] = e.getFormat match {
    case null => None
    case "" => None
    case s => Some( s )
  }
  
  def location: Option[String] = e.getLocation match {
    case null => None
    case "" => None
    case s => Some( s )
  }

}

case class MagicDrawUMLImageImpl
(e: MagicDrawUML#Image, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLImage