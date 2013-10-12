<% import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor as Events %>
<% import org.dbunit.dataset.datatype.* %>
class ${tableName} implements Serializable {
    <%
    def excludedColumns = ['id', 'version']
    //out put the column
    String omitPrefix = "F_"
    def outColumns = []
    def primaryFields = []
    def mappingPFields = []
    def mappingFields = [:]
    def constraintsFields = []

    def idColumn = ["F_ID"]
    def isUseIdColumn = false
    def isUseVersionColumn = true
    outColumns = columns.findAll {!excludedColumns.contains(it.columnName)}
    outColumns.each {p ->
        def outName
        def constraintsField = [:]
        def columnLength = columnsLength[p.columnName]
        def outColumnLength
        if (columnLength == 1) {
            outColumnLength = 'maxSize:1'
        }else{
            outColumnLength = "size:0.." + columnLength
        }
        outName = getOmitPrefixColumn(p.columnName,omitPrefix)
        mappingFields[outName] = p.columnName
        constraintsField['name'] = outName
        def isNullable = p.nullable
        def outNullable
        if (isNullable == 'noNulls') {
            outNullable = 'blank:false,nullable:false'
        }else{
            outNullable = 'blank:true,nullable:true'
        }
        constraintsField['length'] = outColumnLength
        constraintsField['isNull'] = outNullable
        constraintsFields <<  constraintsField
        %>
        ${dataType(p)} ${outName}
        <%}
    primaryKeys.each {p ->
        primaryFields << getOmitPrefixColumn(p.columnName,omitPrefix)
    }
    if (primaryFields.isEmpty()) {
        indexColumns.each{ columnName ->
            primaryFields << getOmitPrefixColumn(columnName,omitPrefix)
        }
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
    if (isUseIdColumn && primaryFields.isEmpty()) {
        primaryFields = idColumn
    }%>
    static mapping = {
        table '${tableName.toUpperCase()}'
        <% if ( !isUseVersionColumn ){ %>
            version false
            <% } %>
        id composite: ['${primaryFields.join("','")}']
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
            %>
            ${column['name']} (${column['isNull']},${column['length']})
            <%
        }
        %>
    }
}