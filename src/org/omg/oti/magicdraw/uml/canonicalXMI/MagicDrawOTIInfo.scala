package org.omg.oti.magicdraw.uml.canonicalXMI

import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.magicdraw.uml.characteristics._

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.uml.characteristics._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._
import org.omg.oti.uml.xmi._

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.language.{implicitConversions, postfixOps}
import scala.reflect.runtime.universe._
import scala.util.control.Exception._
import scala.Option
import scalaz._, Scalaz._

case class MagicDrawOTIInfo
(magicDrawCatalogManager: MagicDrawCatalogManager,
 umlOps: MagicDrawUMLUtil,
 otiProfile: Option[UMLProfile[MagicDrawUML]],
 otiCharacteristicsProvider: OTICharacteristicsProvider[MagicDrawUML]) {
  
}