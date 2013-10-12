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

import java.sql.Connection

includeTargets << grailsScript('_GrailsBootstrap')

USAGE = 'grails database2domain'

target(promptForTableName: "指定表名----") {
    if (!args) {
        ant.input(addProperty: "artifact.name", message: "请输入表名:\nALL --代表所有")
        args = ant.antProject.properties."artifact.name"
    }
}
/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
target(database2domain: 'Reverse-engineers a database and creates domain classes') {
//	depends compile, packageApp, loadApp,
    depends(configureProxy, packageApp, classpath, loadApp, configureApp)
    def tableName = Arrays.asList(args)
    def driverType,driverClassName
    def driverTypes = ['sybase','oracle','mysql']
    def schema
    Connection c
    DataSource ds
    DbunitDatabaseTemplateGenerator generator
    try {
        // do something with connection
        ds = appCtx.getBean('dataSource').getConnection()
        driverClassName = ds.getProperties().get("driverClassName").toString()
        driverTypes.each { dtype ->
            if (driverClassName.contains(dtype)) {
                driverType = dtype
            }
        }
        if (driverType.equals("oracle")) {
            schema = ds.getProperties().get("username").toString()
        }
        generator = new DbunitDatabaseTemplateGenerator()
        generator.generateDomainClasses(c,driverType,'','.',tableName,schema)
    }
    finally {
//    generator?.closeConnection()
        c?.close()
    }
}




setDefaultTarget database2domain
