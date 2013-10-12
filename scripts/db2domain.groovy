/* Copyright 2010-2012 SpringSource.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import grails.plugin.reveng.DbunitDatabaseTemplateGenerator
import grails.util.GrailsUtil

import java.sql.Connection
import java.sql.DriverManager

includeTargets << grailsScript('_GrailsBootstrap')

USAGE = 'grails db2domain 数据库表名'

target(promptForTableName: "指定表名----") {
    if (!args) {
        ant.input(addProperty: "artifact.name", message: "请输入表名:\nALL --代表所有")
        args = ant.antProject.properties."artifact.name"
    }
}
/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
target(db2domain: 'Reverse-engineers a database and creates domain classes') {
	depends compile, packageApp, loadApp,promptForTableName

	try {
		createConfig()

		def generator = classLoader.loadClass('grails.plugin.reveng.DbunitDatabaseTemplateGenerator').newInstance()

        def username    = config.dataSource.username
        def password    = config.dataSource.password
        def databaseUrl = config.dataSource.url
        def driver      = config.dataSource.driverClassName
        def sqlType
        def tableName = args
        def schema
        if (driver.indexOf("sybase", 1) > 0) {
            sqlType = "sybase"
        } else if (driver.indexOf("mysql", 1) > 0) {
            sqlType = "mysql"
        } else if (driver.indexOf("oracle", 1) > 0) {
            sqlType = "oracle"
        } else if (driver.indexOf("hsql", 1) > 0) {
            sqlType = "hsql"
        }
        try {
            Class.forName(driver)
            Connection connection = DriverManager.getConnection(databaseUrl, username, password)
            connection.setAutoCommit true
            println connection.toString()
            println("tableName="+Arrays.asList(tableName).toString())
            println("tableName="+tableName)
            generator.generateDomainClasses(connection,sqlType,'','.',Arrays.asList(tableName),schema)
//            generator.generateDomainClasses(connection,sqlType,'','.',Arrays.asList(tableName))
            println("sqlType="+sqlType)

        } catch (Exception e) {
            e.printStackTrace()

            event("StatusFinal", ["Failed to generate domain classes: ${e.message}"])
            exit(1)
        }
        println("Successfully generated domain classes")
    }
	catch (e) {
		GrailsUtil.deepSanitize e
		throw e
	}
}




setDefaultTarget db2domain
