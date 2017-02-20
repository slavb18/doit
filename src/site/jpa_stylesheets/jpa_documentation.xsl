<?xml version="1.0" encoding="UTF-8"?>
<!--
  jpa_documentation.xsl (2017-01-09)

  An XSLT stylesheet for generating HTML documentation from jpa model by JPAModeler,
  by <slavb18@gmail.com>.

-->

<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:html="http://www.w3.org/1999/xhtml"
    xmlns:exsl="http://exslt.org/common"
    xmlns:ns="urn:namespace"
    xmlns:jpa="http://java.sun.com/xml/ns/persistence/orm"
    extension-element-prefixes="exsl"
    xmlns="http://www.w3.org/1999/xhtml"
    exclude-result-prefixes="xsl xs html ns jpa"
>
    <xsl:import href="markdown.xsl"/>
    <xsl:output 
        method="html" 
        encoding="UTF-8" 
        indent="yes"
        doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
        doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
    />
    <xsl:param name="model_image"/>
    

    <!-- main template -->
        
    <xsl:template match="/jpa:entity-mappings">   
        <html>
            <head>
                <title>
                    <xsl:choose>
                        <xsl:when test="jpa:description">
                            <xsl:value-of select="jpa:description"/>
                        </xsl:when>
                        <xsl:otherwise>My Model</xsl:otherwise>
                    </xsl:choose>                 
                </title>
                <meta charset="utf-8"/>
                <style type="text/css"><![CDATA[
                    body {
                        font-family: sans-serif;
                        font-size: 0.85em;
                        margin: 2em 8em;
                    }
                    h1 {
                        font-size: 2.5em;
                    }
                    h2 {
                        border-bottom: 1px solid black;
                        margin-top: 1em;
                        margin-bottom: 0.5em;
                        font-size: 2em;
                       }
                    h3 {
                        color: orange;
                        font-size: 1.75em;
                        margin-top: 1.25em;
                        margin-bottom: 0em;
                    }
                    h4 {
                        margin: 0em;
                        padding: 0em;
                        border-bottom: 2px solid white;
                    }
                    h6 {
                        font-size: 1.1em;
                        color: #99a;
                        margin: 0.5em 0em 0.25em 0em;
                    }
                    dd {
                        margin-left: 1em;
                    }
                    tt {
                        font-size: 1.2em;
                    }
                    table {
                        margin-bottom: 0.5em;
                    }
                    th {
                        text-align: left;
                        font-weight: normal;
                        color: black;
                        border-bottom: 1px solid black;
                        padding: 3px 6px;
                    }
                    td {
                        padding: 3px 6px;
                        vertical-align: top;
                        background-color: f6f6ff;
                        font-size: 0.85em;
                    }
                    td p {
                        margin: 0px;
                    }
                    ul {
                        padding-left: 1.75em;
                    }
                    p + ul, p + ol, p + dl {
                        margin-top: 0em;
                    }
                    .optional {
                        font-weight: normal;
                        opacity: 0.75;
                    }
                ]]></style>
                <script type="text/javascript"><![CDATA[
                    window.onload = function() {
                        if (location.hash){
                            location = location.hash;
                        }
                    }
                ]]></script>                
            </head>
            <body>
                <h1>
                    <xsl:choose>
                        <xsl:when test="jpa:description">
                            <xsl:value-of select="jpa:description"/>
                        </xsl:when>
                        <xsl:otherwise>My Model</xsl:otherwise>
                    </xsl:choose>
                </h1>
                <xsl:variable name="package" select="@pkg"/>
                <ol>
                    <xsl:apply-templates select="jpa:entity" mode="toc"/>
                </ol>
                <h2 id="entities">Entities</h2>
                <xsl:if test="$model_image">
                    <img src="{$model_image}"/>
                </xsl:if>
                <xsl:apply-templates select="jpa:entity" mode="list">
                    <xsl:with-param name="package" select="$package"/>
                </xsl:apply-templates>                    
            </body>
        </html>
    </xsl:template>

    <!-- Table of Contents -->

    <xsl:template match="jpa:entity" mode="toc">
        <xsl:variable name="ref">
            <xsl:call-template name="get-entity-ref"/>
        </xsl:variable>
        <xsl:variable name="name">
            <xsl:call-template name="get-entity-name"/>
        </xsl:variable>
        
        <li>
            <a href="#{$ref}">
                <xsl:value-of select="$name"/>
            </a>
        </li>
        
    </xsl:template>


    <!-- Listings -->
    
    <xsl:template match="jpa:entity" mode="list">
        <xsl:param name="package"/>
        <xsl:variable name="ref">
            <xsl:call-template name="get-entity-ref"/>
        </xsl:variable>
        <xsl:variable name="name">
            <xsl:call-template name="get-entity-name"/>
        </xsl:variable>
        <div class="entity">
            <h3 id="{$ref}">
                <xsl:value-of select="$name"/>
            </h3>
            <p>URN: <xsl:value-of select="concat($package,'.',@class)"/></p>
            <xsl:if test="@superclassId">
                
            </xsl:if>
            <p>
                <xsl:call-template name="get-entity-description"/>
            </p>
            <table>
                <tr>
                    <th>attribute</th>
                    <th>type</th>
                    <th>flags</th>
                    <th>description</th>
                </tr>
                <xsl:apply-templates select="jpa:attributes/jpa:*" mode="attributes"/>

            </table>
        </div>
        
    </xsl:template>
    
    <xsl:template match="jpa:basic|jpa:id" mode="attributes">
        <tr>
            <td>
                <xsl:value-of select="@name"/>
            </td>
            <td>
                <xsl:value-of select="@attribute-type"/>
                <xsl:if test="@length">(<xsl:value-of select="@length"/>)</xsl:if>
            </td>
            <td>
                <xsl:if test="local-name(.)='id'">PK</xsl:if>
                <xsl:if test="jpa:column/@unique='true'">UQ</xsl:if>
            </td>
            <td>
                <xsl:value-of select="jpa:des"/>
            </td>            
        </tr>
        
    </xsl:template>
    <xsl:template match="jpa:many-to-one" mode="attributes">
            <xsl:if test="@optional='false' and not (jpa:join-column/@nullable='false')">
                <xsl:message terminate="no">
                    <xsl:value-of select="concat('Warning: ', ancestor-or-self::jpa:entity/@class, '.',@name,' is not optional, but join column is nullable')"/>
                </xsl:message>

                
            </xsl:if>
        
        <tr>
            <td>
                <xsl:value-of select="@name"/>
            </td>
            <td>
                <xsl:call-template name="get-entity-link">
                    <xsl:with-param name="id" select="@connected-entity-id"/>
                </xsl:call-template>
            </td>
            <td>
                
            </td>
            <td>
                <xsl:value-of select="jpa:des"/>
            </td>
        </tr>
    </xsl:template>
            
    <!-- utilities -->
    <xsl:template name="get-entity-link">
        <xsl:param name="id"/>
        <xsl:variable name="conRef">
            <xsl:for-each select="/jpa:entity-mappings/jpa:entity[@id=$id]">
                <xsl:call-template name="get-entity-ref"/>
            </xsl:for-each>
        </xsl:variable>
        <xsl:variable name="conName">
            <xsl:for-each select="/jpa:entity-mappings/jpa:entity[@id=$id]">
                <xsl:call-template name="get-entity-name"/>
            </xsl:for-each>
        </xsl:variable>
        <a href="#{$conRef}">
            <xsl:value-of select="$conName"/>
        </a>
    </xsl:template>

    <xsl:template name="get-entity-ref">
        <xsl:choose>
            <xsl:when test="@class">
                <xsl:value-of select="@class"/>
            </xsl:when>
            <xsl:when test="@id">
                <xsl:value-of select="@id"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="generate-id()"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="get-entity-name">
        <xsl:choose>
            <xsl:when test="jpa:des and contains(jpa:des, '&#10;')">
                <xsl:value-of select="substring-before(jpa:des, '&#10;')"/>
            </xsl:when>
            <xsl:when test="jpa:des">
                <xsl:value-of select="jpa:des"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="@class"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="get-entity-description">
        <xsl:choose>
            <xsl:when test="jpa:des">
                <xsl:call-template name="block">
                    <xsl:with-param name="pString" select="substring-after(jpa:des, '&#10;')"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- entity-encode markup for display -->

    <xsl:template match="*" mode="encode">
        <xsl:text>&lt;</xsl:text>
        <xsl:value-of select="name()"/>
        <xsl:apply-templates select="attribute::*" mode="encode"/>
        <xsl:choose>
            <xsl:when test="*|text()">
                <xsl:text>&gt;</xsl:text>
                <xsl:apply-templates select="*|text()" mode="encode" xml:space="preserve"/>
                <xsl:text>&lt;/</xsl:text>
                <xsl:value-of select="name()"/>
                <xsl:text>&gt;</xsl:text>                
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>/&gt;</xsl:text>
            </xsl:otherwise>
        </xsl:choose>    
    </xsl:template>            
    
    <xsl:template match="@*" mode="encode">
        <xsl:text> </xsl:text>
        <xsl:value-of select="name()"/>
        <xsl:text>="</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text>"</xsl:text>
    </xsl:template>    
    
    <xsl:template match="text()" mode="encode">
        <xsl:value-of select="." xml:space="preserve"/>
    </xsl:template>    

    <!-- copy HTML for display -->
    
    <xsl:template match="html:*" mode="copy">
        <!-- remove the prefix on HTML elements -->
        <xsl:element name="{local-name()}">
            <xsl:for-each select="@*">
                <xsl:attribute name="{local-name()}">
                    <xsl:value-of select="."/>
                </xsl:attribute>
            </xsl:for-each>
            <xsl:apply-templates select="node()" mode="copy"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="@*|node()[namespace-uri()!='http://www.w3.org/1999/xhtml']" mode="copy">
        <!-- everything else goes straight through -->
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" mode="copy"/>
        </xsl:copy>
    </xsl:template>    

</xsl:stylesheet>
