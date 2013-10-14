grails.doc.authors = 'Burt Beckwith'
grails.doc.license = 'Apache License 2.0'
grails.doc.title = 'Database Reverse Engineering Plugin'
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"



grails {
    plugin {
        reveng{
            dataSource.driverClassName = "com.sybase.jdbc3.jdbc.SybDriver"
//            dialect = "org.hibernate.dialect.Sybase11Dialect"
            dataSource.username = "aa"
            dataSource.password = "bb"
            dataSource.url = "jdbc:sybase:Tds:100.168.209.116:5000/sloam"
            packageName = 'com.pansoft.module.oa.dict'
//            url = "jdbc:sybase:Tds:100.168.209.116:5000/sloam?CHARSET_CONVERTER_CLASS=com.sybase.jdbc2.utils.Cp850PureConverter"
        }
    }

}