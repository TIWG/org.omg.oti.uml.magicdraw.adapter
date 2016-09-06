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

package org.omg.oti.magicdraw.uml.canonicalXMI

import org.omg.oti.json.uml.serialization.OTIJsonElementHelper
import org.omg.oti.magicdraw.uml.characteristics.{MagicDrawOTICharacteristicsDataProvider, MagicDrawOTICharacteristicsProfileProvider}
import org.omg.oti.magicdraw.uml.read.{MagicDrawUML, MagicDrawUMLUtil}
import org.omg.oti.magicdraw.uml.write.{MagicDrawUMLFactory, MagicDrawUMLUpdate}
import org.omg.oti.uml.characteristics.OTICharacteristicsProvider
import org.omg.oti.uml.canonicalXMI.helper.{OTIAdapter, OTIDocumentSetAdapter, OTIResolvedDocumentSetAdapter, OTIResolvedDocumentSetGeneratorAdapter}

package object helper {

  type MagicDrawOTIAdapter =
  OTIAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      OTICharacteristicsProvider[MagicDrawUML],
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate ]

  type MagicDrawOTIProfileAdapter =
  OTIAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      MagicDrawOTICharacteristicsProfileProvider,
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate ]

  type MagicDrawOTIDataAdapter =
  OTIAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      MagicDrawOTICharacteristicsDataProvider,
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate ]

  type MagicDrawOTIJsonElementHelperForProfileAdapter =
  OTIJsonElementHelper
  [ MagicDrawUML,
    MagicDrawUMLUtil,
    MagicDrawOTICharacteristicsProfileProvider,
    MagicDrawUMLFactory,
    MagicDrawUMLUpdate,
    MagicDrawDocumentSet ]

  type MagicDrawOTIJsonElementHelperForDataAdapter =
  OTIJsonElementHelper
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      MagicDrawOTICharacteristicsDataProvider,
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentSet ]

  type MagicDrawOTIDocumentSetAdapter =
  OTIDocumentSetAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      OTICharacteristicsProvider[MagicDrawUML],
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentOps,
      MagicDrawDocumentSet]

  type MagicDrawOTIDocumentSetAdapterForProfileProvider =
  OTIDocumentSetAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      MagicDrawOTICharacteristicsProfileProvider,
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentOps,
      MagicDrawDocumentSet ]

  type MagicDrawOTIDocumentSetAdapterForDataProvider =
  OTIDocumentSetAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      MagicDrawOTICharacteristicsDataProvider,
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentOps,
      MagicDrawDocumentSet]

  type MagicDrawOTIResolvedDocumentSetAdapterForProfileProvider =
  OTIResolvedDocumentSetAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      MagicDrawOTICharacteristicsProfileProvider,
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentOps,
      MagicDrawDocumentSet]

  type MagicDrawOTIResolvedDocumentSetAdapterForDataProvider =
  OTIResolvedDocumentSetAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      MagicDrawOTICharacteristicsDataProvider,
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentOps,
      MagicDrawDocumentSet]

  type MagicDrawOTIResolvedDocumentSetAdapter =
  OTIResolvedDocumentSetAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      OTICharacteristicsProvider[MagicDrawUML],
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentOps,
      MagicDrawDocumentSet]

  type MagicDrawOTIResolvedDocumentSetIDGeneratorAdapter =
  OTIResolvedDocumentSetGeneratorAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      OTICharacteristicsProvider[MagicDrawUML],
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentOps,
      MagicDrawDocumentSet,
      MagicDrawIDGenerator]

  type MagicDrawOTIResolvedDocumentSetHashIDGeneratorAdapter =
  OTIResolvedDocumentSetGeneratorAdapter
    [ MagicDrawUML,
      MagicDrawUMLUtil,
      OTICharacteristicsProvider[MagicDrawUML],
      MagicDrawUMLFactory,
      MagicDrawUMLUpdate,
      MagicDrawDocumentOps,
      MagicDrawDocumentSet,
      MagicDrawHashIDGenerator]
}