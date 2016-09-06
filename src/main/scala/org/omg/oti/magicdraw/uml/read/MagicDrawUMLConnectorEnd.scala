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
import scala.{Option,None,Some,StringContext}
import scala.Predef.String

trait MagicDrawUMLConnectorEnd 
  extends MagicDrawUMLMultiplicityElement
  with UMLConnectorEnd[MagicDrawUML] {

  override protected def e: Uml#ConnectorEnd
  def getMagicDrawConnectorEnd = e

  override implicit val umlOps = ops
  import umlOps._

  override def partWithPort: Option[UMLProperty[Uml]] =
    for { result <- Option.apply(e.getPartWithPort) } yield result
  
  override def role: Option[UMLConnectableElement[Uml]] =
    for { result <- Option.apply(e.getRole) } yield result

}

case class MagicDrawUMLConnectorEndImpl
(e: MagicDrawUML#ConnectorEnd, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLConnectorEnd
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLConnectorEnd(ID=${e.getID}, " +
      s"role=${role match { case Some(c) => c.toolSpecific_id; case None => "<none>"}}, " +
      s"connector=${end_connector match { case Some(c) => c.toolSpecific_id; case None => "<none>"}})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}