package com.example.mybatis.model;

import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ProductsRequestElastic {
    @ElasticField(name = "id")
    protected Long id;
    @ElasticField(name = "name.keyword")
    protected String name;
    @ElasticField(name = "userId")
    protected Long userId;

    public BoolQueryBuilder createQuery(ProductsRequestElastic obj) {
        BoolQueryBuilder bool = new BoolQueryBuilder();

        Class<?> cls = obj.getClass();
        List<String> fieldsNotNull = new ArrayList<>();
        for (Field field : cls.getDeclaredFields()) {
            ElasticField elasticField = field.getAnnotation(ElasticField.class);
            field.setAccessible(true);
            Object value = ReflectionUtils.getField(field, obj);

            if (value == null) continue;

            bool.must(QueryBuilders.matchQuery(elasticField.name(), value));
            fieldsNotNull.add(field.getName());
        }

        if (fieldsNotNull.isEmpty()) {
            throw new NoSuchElementFoundException("All fields is null");
        }

        List<String[]> groups = getGroup();
        for (String[] group : groups) {
            if (fieldsNotNull.equals(Arrays.asList(group)))
                return bool;
        }

        throw new NoSuchElementFoundException(String.format("Not permit group, group available %s", Arrays.toString(groups.toArray())));
    }

    private List<String[]> getGroup() {
        List<String[]> groups = new ArrayList<>();
        groups.add(new String[]{"id"});
        groups.add(new String[]{"name", "userId"});

        return groups;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface ElasticField {
        String name();
    }
}
