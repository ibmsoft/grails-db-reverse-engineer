package grails.plugin.reveng

import org.codehaus.groovy.grails.scaffolding.DefaultGrailsTemplateGenerator

import org.apache.commons.logging.Log;


import org.apache.commons.logging.LogFactory;


import org.codehaus.groovy.grails.commons.ApplicationHolder
import java.sql.Connection
import org.dbunit.database.IDatabaseConnection
import org.dbunit.ext.mssql.MsSqlConnection
import org.dbunit.dataset.IDataSet
import org.dbunit.ext.mysql.MySqlConnection
import org.dbunit.ext.oracle.OracleConnection
import org.dbunit.ext.hsqldb.HsqldbConnection
import org.dbunit.dataset.ITableMetaData

import java.sql.DatabaseMetaData
import java.sql.ResultSet;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-5-21
 * Time: 10:18:13
 * To change this template use File | Settings | File Templates.
 */
/**
 *  use the underlaying database table to generate the domain
 *  根据数据库中表形成domain Class
 */
class DbunitDatabaseTemplateGenerator extends DefaultGrailsTemplateGenerator {
    static final Log Dbunitlog = LogFactory.getLog(DbunitDatabaseTemplateGenerator.class);

    public IDatabaseConnection conn
    public IDataSet dataSet
//    public String[] tables
    def tables = []
    public DatabaseMetaData databaseMetaData
    def indexColumns = []
    def columnsLength = [:]
    public ResultSet resultSet,resultSet1

    // 定义对应的数据类型
    def dataType = {column ->
//        def column = property
//        def cp = domainClass.constrainedProperties[property.name]

        if (!renderEditorTemplate) {
            // create template once for performance
            def templateText = getTemplateText("dataType.template")
            renderEditorTemplate = engine.createTemplate(templateText)
            Dbunitlog.info("templateText=" + templateText)
        }


        def binding = [column: column]
        Dbunitlog.info("binding=" + binding)
        return renderEditorTemplate.make(binding).toString()
    }

//    public DbunitDatabaseTemplateGenerator(Connection con,String sqlType) {
//
//
//
//    }

    // get the tables from the dataset
    public void generateDomainClasses(Connection conn, String sqlType, String pkg, String destDir, List tableName,String schema) {
        switch (sqlType) {
            case 'sybase': this.conn = new MsSqlConnection(conn, schema)
                break
            case 'mysql': this.conn = new MySqlConnection(conn, schema)
                break
            case 'oracle': this.conn = new OracleConnection(conn, schema)
                break
            case 'hsql': this.conn = new HsqldbConnection(conn, schema)
                break

        }

        dataSet = this.conn.createDataSet()
        tables = Arrays.asList(dataSet.getTableNames())
        def tableTmp = []
        if (tableName.size() > 0) {
            tableName.each {
                tableTmp << it.toLowerCase()
            }
        }

        if (tableTmp.contains("all")) {

        } else {
            def tmp = []
            tmp = tables.findAll {
                tableTmp.contains(it.toLowerCase())
            }.asList()
            tables = tmp
        }

        databaseMetaData = this.conn.getConnection().getMetaData()
        tables.each {
            indexColumns.clear()
            columnsLength.clear()
            resultSet = databaseMetaData.getIndexInfo(null, this.conn.schema, it, true, false)
//            resultSet = databaseMetaData.getBestRowIdentifier(null, this.conn.schema, it, DatabaseMetaData.bestRowSession, true)
            while (resultSet.next()) {
//                indexUnique = resultSet.getBoolean(4)
//                indexColumns['table'] = resultSet.getString(3)
//                indexColumns['unique'] = resultSet.getString(6)
//                indexColumns['type'] = resultSet.getString(7)
                if (resultSet.getBoolean(4)) {
                    //todo only add unique index

                }
                if (resultSet.getString(9) != 'null' && !resultSet.getString(9).is(null)) {
                    indexColumns << resultSet.getString(9)
                }


            }
            // for the columnslength
            resultSet1 = databaseMetaData.getColumns(null,this.conn.schema,it,"%")
            while (resultSet1.next()) {
                def sname = resultSet1.getString("COLUMN_NAME");
                def slength = resultSet1.getInt("COLUMN_SIZE").toInteger();
                columnsLength[sname] = slength
            }
            generateDomain(columnsLength,dataSet.getTableMetaData(it), pkg, destDir)

        }
//        this.conn.close()
    }
    // generate domains from the tables
    public void generateDomain(def columnsLength,ITableMetaData tableMetaData, String pkg, String destdir) {
        if (!destdir)
            throw new IllegalArgumentException("Argument [destdir] not specified")

        if (tableMetaData.tableName) {
            Dbunitlog.info("Domain generated at ${tableMetaData.tableName}")
            System.out.println("tableName=" + tableMetaData.tableName)
//            def fullName = domainClass.fullName
//            def pkg = ""
//            def pos = fullName.lastIndexOf('.')
//            if (pos != -1) {
//                // Package name with trailing '.'
//                pkg = fullName[0..pos]
//            }

            def destFile = new File("${destdir}/grails-app/domain/${tableMetaData.tableName[0] + tableMetaData.tableName[1..-1].toLowerCase()}.groovy")
            if (canWrite(destFile)) {
                destFile.parentFile.mkdirs()

                destFile.withWriter {w ->
                    generateDomain(columnsLength,tableMetaData, w)
                }

                Dbunitlog.info("Domain generated at ${destFile}")
            }
        }
    }

    public void generateDomain(def columnsLength,ITableMetaData tableMetaData, Writer out) {
        def templateText = getTemplateText("Domain.groovy")
        def binding = [
                tableName: tableMetaData.tableName[0] + tableMetaData.tableName[1..-1].toLowerCase(),
                columns: tableMetaData.columns,
                columnsLength:columnsLength,
                primaryKeys: tableMetaData.primaryKeys,
                indexColumns: indexColumns,
                dataType: dataType,
                comparator: org.codehaus.groovy.grails.scaffolding.DomainClassPropertyComparator.class]

        def t = engine.createTemplate(templateText)
        t.make(binding).writeTo(out)
    }



    private canWrite(testFile) {
        if (!overwrite && testFile.exists()) {
            try {
                ant.input(message: "File ${testFile} already exists. Overwrite?", "y,n,a", addproperty: "overwrite.${testFile.name}")
                overwrite = (ant.antProject.properties."overwrite.${testFile.name}" == "a") ? true : overwrite
                return overwrite || ((ant.antProject.properties."overwrite.${testFile.name}" == "y") ? true : false)
            } catch (Exception e) {
                // failure to read from standard in means we're probably running from an automation tool like a build server
                return true
            }
        }
        return true
    }

    public getTemplateText(String template) {
        def application = grailsApplication
        // first check for presence of template in application
        if (resourceLoader && application?.warDeployed) {
            return resourceLoader.getResource("/WEB-INF/templates/scaffolding/${template}").inputStream.text
        }
        else {
            def templateFile = "${basedir}/src/templates/scaffolding/${template}"
            if (!new File(templateFile).exists()) {
                // template not found in application, use default template
                def ant = new AntBuilder()
                ant.property(environment: "env")
                def grailsHome = ant.antProject.properties."env.GRAILS_HOME"
                templateFile = "${grailsHome}/src/grails/templates/scaffolding/${template}"
            }
            return new File(templateFile).getText()
        }
    }

    public void closeConnection() {
        if (this.conn) {
            this.conn.close()
        }
    }


}
