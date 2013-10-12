
dataSource {
    dbCreate = "update"   // one of 'create', 'create-drop','update'
    driverClassName = "com.sybase.jdbc2.jdbc.SybDriver"
    dialect = "org.hibernate.dialect.Sybase11Dialect"
    username = "sa"
    password = ""
    url = "jdbc:sybase:Tds:10.68.209.116:5000/sloam"
//            url = "jdbc:sybase:Tds:10.68.209.116:5000/sloam?CHARSET_CONVERTER_CLASS=com.sybase.jdbc2.utils.Cp850PureConverter"
}


hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
    cache.provider_class = 'org.hibernate.cache.EhCacheProvider'
}
