# MagicDraw 18.0 Adapter for OMG Tool Interchange (OTI) API

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