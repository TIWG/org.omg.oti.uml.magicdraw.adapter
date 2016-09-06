# MagicDraw adapter for the OMG Tool-Neutral Interoperability (OTI) API

[![Build Status](https://travis-ci.org/TIWG/org.omg.oti.uml.magicdraw.adapter.svg?branch=master)](https://travis-ci.org/TIWG/org.omg.oti.uml.magicdraw.adapter)
[ ![Download](https://api.bintray.com/packages/tiwg/org.omg.tiwg/org.omg.oti.uml.magicdraw.adapter/images/download.svg) ](https://bintray.com/tiwg/org.omg.tiwg/org.omg.oti.uml.magicdraw.adapter/_latestVersion)
 
## MagicDraw-specific resources

- `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.mdzip`

This is the "source" MagicDraw 18.0 adaptation of the OMG Tool Infrastructure ancilary resources.
The OTI ID Generation algorithm has been applied to the `OMG Tool Infrastructure::OTI` profile.
This results in a change migration file: `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.migration.xmi`
See `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.migration.tiff` for a screenshot of the results.

- `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure 4 MagicDraw.mdzip`

Install this file in MagicDraw folder as `<install.root>/profiles/OMG/OMG Tool Infrastructure 4 MagicDraw.mdzip`

This is the result of applying the OTI ChangeMigration algorithm
to `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.mdzip`,
which effectively changed all the xmi:IDs according
to `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.migration.xmi`

## When updating: `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.mdzip`

1. MD Open: `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.mdzip`

2. Select the `OMG Tool Infrastructure::OTI` profile

3. Menu: `DynamicScriptsContextMenu > OMG Tool Infrastructure > Generate OTI Canonical XMI:IDs for selected Profile extents`

4. At the dialog prompt: `Specify the relative path of the previous module version` enter:

   `dynamicScripts/org.omg.oti.magicdraw/resources/OMG%20Tool%20Infrastructure%204%20MagicDraw.mdzip`

   (see: `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.migration-relativePath.tiff`)

5. Click OK

6. At the end, the result should look as shown here:

   `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.migration.tiff`

   This results in an OTI ChangeMigration XMI file that can be used for
   changing the XMI:ID and XMI:UUID of the project itself or of other MagicDraw projects
   that cross-reference it.

   `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure.migration.xmi`

7. MD Save As: `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure 4 MagicDraw.mdzip`

8. MD Toolbar: `DynamicScripts > OMG > Migration Support > Change XMI:IDs per project migration.xmi`

   This step is the one that actually updates the xmi:IDs and xmi:UUIDs


9. Copy `org.omg.oti.magicdraw/resources/OMG Tool Infrastructure 4 MagicDraw.mdzip` to
   MagicDraw's installation folder as: `<install.root>/profiles/OMG Tool Infrastructure 4 MagicDraw-version[SVN Rev #].mdzip`

   Replace `[SVN Rev #]` with the actual SVN Revision number.

