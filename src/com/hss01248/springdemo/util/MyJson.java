package com.hss01248.springdemo.util;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.Annotated;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.node.TextNode;
import org.codehaus.jackson.type.TypeReference;

import javax.persistence.*;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MyJSON {

    public static String toJsonStr(Object obj){
        try
        {
            return getObjectMapper().writeValueAsString(obj);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public static <T> T  parseObject(String str,Class<T> clazz){
        try {
            return getObjectMapper().readValue(str, clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
        }return null;
    }

    /*public static  <E> List<E> parseArray(String str,Class<E> clazz){
        return JSON.parseArray(str,clazz);
    }*/







    public static void writeJsonString(Object object, Writer writer)
    {
        try
        {
            getObjectMapper().writeValue(writer, object);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Map<?, ?> parseJson(String jsonString)
    {
        JsonNode jn = null;
        try {
            jn = getObjectMapper().readTree(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return (Map<?, ?>)JsonNodeToMap(jn);
    }

    public static Object parseJson2MapOrList(String jsonString)
    {
        JsonNode jn = null;
        try {
            jn = getObjectMapper().readTree(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return JsonNodeToMap(jn);
    }

    public static <T> T parseJson(String jsonString, Class<T> classType)
    {
        try {
            return getObjectMapper().readValue(jsonString, classType);
        } catch (Exception ex) {
            ex.printStackTrace();
        }return null;
    }

    public static <T> T parseJson(String jsonString, TypeReference<T> typeReference)
    {
        try
        {
            return getObjectMapper().readValue(jsonString, typeReference);
        } catch (Exception ex) {
            ex.printStackTrace();
        }return null;
    }

    public static <T> T parseJsonT(String jsonString)
    {
        try
        {
            return getObjectMapper().readValue(jsonString, new TypeReference<Object>() { } );
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }return null;
    }

    public static <T> Map<?, ?> bean2Map(Object bean)
    {
        try
        {
            return (Map<?, ?>)getObjectMapper().convertValue(bean, Map.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }return null;
    }

    public static <T> T map2Bean(Map<?, ?> map, Class<T> clazz)
    {
        try
        {
            return getObjectMapper().convertValue(map, clazz);
        } catch (Exception ex) {
            ex.printStackTrace();
        }return null;
    }

    public static Object JsonNodeToMap(JsonNode root)
    {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (root == null) {
            return map;
        }
        if (root.isArray()) {
            List<Object> list = new ArrayList<Object>();
            for (JsonNode node : root) {
                Object nmp = JsonNodeToMap(node);
                list.add(nmp);
            }
            return list;
        }if (root.isTextual()) {
        try {
            return ((TextNode)root).asText();
        } catch (Exception e) {
            return root.toString();
        }
    }
        Iterator<?> iter = root.getFields();
        while (iter.hasNext()) {
            @SuppressWarnings("rawtypes")
            Map.Entry entry = (Map.Entry)iter.next();
            String key = (String)entry.getKey();
            JsonNode ele = (JsonNode)entry.getValue();
            if (ele.isObject())
                map.put(key, JsonNodeToMap(ele));
            else if (ele.isTextual())
                map.put(key, ((TextNode)ele).asText());
            else if (ele.isArray())
                map.put(key, JsonNodeToMap(ele));
            else {
                map.put(key, ele.toString());
            }
        }
        return map;
    }














    public static ObjectMapper getObjectMapper() {
        ObjectMapper om = new ObjectMapper().setAnnotationIntrospector(new JpaLazyIntrospector());

        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        om.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        om.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        om.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        om.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        om.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        return om;
    }


  static   class JpaLazyIntrospector extends JacksonAnnotationIntrospector
    {
        public boolean isHandled(Annotation ann)
        {
            boolean handled = super.isHandled(ann);
            if (!handled) {
                handled = ann.toString().startsWith("@javax.persistence");
            }
            if (!handled) {
                handled = ann.annotationType().equals(JsonLazy.class);
            }
            return handled;
        }

        protected boolean _isIgnorable(Annotated a)
        {
            boolean ignor = super._isIgnorable(a);
            if (!ignor) {
                ignor = isLazy(a);
            }
            return ignor;
        }

        private boolean isLazy(Annotated a) {
            boolean lazy = false;

            JsonLazy jsonLazy = (JsonLazy)a.getAnnotation(JsonLazy.class);
            if (jsonLazy != null) {
                return false;
            }

            Basic basic = (Basic)a.getAnnotation(Basic.class);
            if ((basic != null) && (basic.fetch() == FetchType.LAZY)) {
                lazy = true;
            }

            ElementCollection elementCollection = (ElementCollection)a.getAnnotation(ElementCollection.class);
            if ((elementCollection != null) && (elementCollection.fetch() != FetchType.EAGER)) {
                lazy = true;
            }

            ManyToMany manyToMany = (ManyToMany)a.getAnnotation(ManyToMany.class);
            if ((manyToMany != null) && (manyToMany.fetch() != FetchType.EAGER)) {
                lazy = true;
            }

            OneToMany oneToMany = (OneToMany)a.getAnnotation(OneToMany.class);
            if ((oneToMany != null) && (oneToMany.fetch() != FetchType.EAGER)) {
                lazy = true;
            }

            ManyToOne manyToOne = (ManyToOne)a.getAnnotation(ManyToOne.class);
            if ((manyToOne != null) && (manyToOne.fetch() == FetchType.LAZY)) {
                lazy = true;
            }

            OneToOne oneToOne = (OneToOne)a.getAnnotation(OneToOne.class);
            if ((oneToOne != null) && (oneToOne.fetch() == FetchType.LAZY)) {
                lazy = true;
            }
            return lazy;
        }
    }


}
