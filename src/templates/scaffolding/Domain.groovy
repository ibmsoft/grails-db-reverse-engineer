<% import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor as Events %>
<% import org.dbunit.dataset.datatype.* %>
class ${tableName} {
    <%
    def excludedColumns = ['id', 'version']
    //out put the column
    String omitPrefix = "F_"
    def outColumns = []
    def primaryFields = []
    def mappingFields = [:]
    def constraintsFields = [:]
    def idColumn = ["F_ID"]
    def isUseIdColumn = true
    def isUseVersionColumn = false
    outColumns = columns.findAll {!excludedColumns.contains(it.columnName)}
    outColumns.each {p ->
        def outName
        outName = getOmitPrefixColumn(p.columnName,omitPrefix)
        mappingFields[outName] = p.columnName
        constraintsFields[outName] = p.nullable
        %>
        ${dataType(p)} ${outName}
        <%
    }
    primaryKeys.each {p ->
        primaryFields << getOmitPrefixColumn(p.columnName,omitPrefix)
    }

    if (primaryFields.isEmpty()) {
        primaryFields = indexColumns
    }

    public String getOmitPrefixColumn(String orignalColumn,String omitPrefixString) {
        String  omitPrefixColumn
        if (orignalColumn.contains(omitPrefixString)){
            omitPrefixColumn = orignalColumn[omitPrefixString.length()..-1].toLowerCase()
        }else {
            omitPrefixColumn = orignalColumn.toLowerCase()
        }
        return   omitPrefixColumn
    }
    if (isUseIdColumn) {
        primaryFields = idColumn
    }


    %>
    static mapping = {
        // table '${tableName}'
        <% if ( !isUseVersionColumn ){ %>
            version false
            id composite: ['${primaryFields.join(', ')}']
            <% } %>

        <%
        mappingFields.each {key,value ->
            %>
            ${key} column: '${value}'
            <%
        }
        %>
    }

    static constraints = {
        <%
        constraintsFields.each {column ->
            if ("${column.value}" == "noNulls") {
                %>
                ${column.key} (nullable: false)
                <%
            }
        }
        %>
    }
}