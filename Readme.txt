功能介绍：
从数据库表形成 domain
可以使用原来的 reverse 提供的方法，同时增加其他的实现方法

使用方法：
1、执行 install-domain-template
2、在 conifg.groovy 配置 包名称
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
3、如果想定制生产的 domain 可以修改 scaffolding目录下的 Domain.groovy 或者 dataType.template

