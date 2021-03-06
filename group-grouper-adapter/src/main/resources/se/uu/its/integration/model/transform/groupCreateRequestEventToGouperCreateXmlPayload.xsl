<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:uuig="http://www.uu.se/schemas/integration/2015/Group"
	xmlns:uuie="http://www.uu.se/schemas/integration/2015/Events"
	exclude-result-prefixes="uuig uuie">

	<xsl:output method="xml" omit-xml-declaration="no" indent="no" />

	<xsl:template match="/">

		<WsRestGroupSaveRequest>
			<wsGroupToSaves>
				<WsGroupToSave>
					<wsGroupLookup>
						<groupName><xsl:value-of select="uuie:GroupEvent/uuig:Group/uuig:Name"/></groupName>
					</wsGroupLookup>
					<wsGroup>
						<displayExtension><xsl:value-of select="uuie:GroupEvent/uuig:Group/uuig:DisplayName"/></displayExtension>
						<description><xsl:value-of select="uuie:GroupEvent/uuig:Group/uuig:Description"/></description>
						<name><xsl:value-of select="uuie:GroupEvent/uuig:Group/uuig:Name"/></name>
					</wsGroup>
				</WsGroupToSave>
			</wsGroupToSaves>
		</WsRestGroupSaveRequest>

	</xsl:template>

</xsl:stylesheet>
