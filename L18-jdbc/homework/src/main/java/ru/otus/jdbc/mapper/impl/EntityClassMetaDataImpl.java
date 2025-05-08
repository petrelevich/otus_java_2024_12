package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.crm.annotation.Column;
import ru.otus.crm.annotation.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final List<Field> allFields;
    private final Field idField;
    private final List<Field> fieldsWithoutId;
    private final Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.allFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .collect(Collectors.toList());
        this.idField = findIdField();
        this.fieldsWithoutId = filterFieldsWithoutId();
        this.constructor = findConstructor();
    }

    private Field findIdField() {
        List<Field> idFields =
                allFields.stream().filter(f -> f.isAnnotationPresent(Id.class)).collect(Collectors.toList());
        if (idFields.size() != 1) {
            throw new IllegalArgumentException("Класс должен содержать одно поле с аннотацией @Id");
        }
        return idFields.get(0);
    }

    private List<Field> filterFieldsWithoutId() {
        return allFields.stream().filter(f -> f != idField).collect(Collectors.toList());
    }

    private Constructor<T> findConstructor() {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Отсутствует конструктор по умолчанию", e);
        }
    }

    @Override
    public String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && !column.name().isEmpty() ? column.name() : field.getName();
    }

    @Override
    public String getName() {
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
