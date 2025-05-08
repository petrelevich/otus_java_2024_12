package ru.otus.jdbc.mapper.impl;

import java.util.stream.Collectors;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> metaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> metaData) {
        this.metaData = metaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT * FROM %s", metaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        String idColumn = metaData.getColumnName(metaData.getIdField());
        return String.format("SELECT * FROM %s WHERE %s = ?", metaData.getName(), idColumn);
    }

    @Override
    public String getInsertSql() {
        String columns = metaData.getFieldsWithoutId().stream()
                .map(metaData::getColumnName)
                .collect(Collectors.joining(", "));
        String placeholders =
                metaData.getFieldsWithoutId().stream().map(f -> "?").collect(Collectors.joining(", "));
        return String.format("INSERT INTO %s (%s) VALUES (%s)", metaData.getName(), columns, placeholders);
    }

    @Override
    public String getUpdateSql() {
        String setClause = metaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));
        return String.format(
                "UPDATE %s SET %s WHERE %s = ?",
                metaData.getName(), setClause, metaData.getIdField().getName());
    }
}
