//        ${dataType(p)} ${outName}

if (column.sqlTypeName instanceof java.lang.String )
        out << String
    else if (column.sqlTypeName instanceof java.lang.Integer)
        out << Integer
    else