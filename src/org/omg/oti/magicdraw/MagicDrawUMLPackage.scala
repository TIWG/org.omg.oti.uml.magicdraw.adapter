package org.omg.oti.magicdraw

import org.omg.oti._
import scala.collection.JavaConversions._

trait MagicDrawUMLPackage extends UMLPackage[MagicDrawUML] with MagicDrawUMLNamespace with MagicDrawUMLPackageableElement {
  override protected def e: Uml#Package

  import ops._

  def URI = e.getURI match {
    case null => None
    case ""   => None
    case uri  => Some( uri )
  }
}