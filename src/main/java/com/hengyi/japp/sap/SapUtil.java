package com.hengyi.japp.sap;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;
import static org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptor;
import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import static org.apache.commons.beanutils.PropertyUtils.setSimpleProperty;
import static org.apache.commons.lang3.StringUtils.upperCase;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.hengyi.japp.sap.annotation.SapConvertField;
import com.hengyi.japp.sap.annotation.SapTransient;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;

public class SapUtil {
    public static <T> List<T> convert(Class<T> clazz, JCoTable t) throws Exception {
        List<T> result = Lists.newArrayList();
        t.firstRow();
        for (int i = 0; i < t.getNumRows(); i++, t.nextRow()) {
            JCoRecord r = t;
            result.add(convert(clazz, r));
        }
        return result;
    }

    public static <T> T convert(Class<T> clazz, JCoRecord record) throws Exception {
        T t = clazz.newInstance();
        fill(t, record);
        return t;
    }
    
    public static void fill(Object des, JCoRecord src) throws Exception {
        for (Entry<String, String> entry : cache.get(des.getClass()).entrySet()) {
        	String beanPropertyName = entry.getKey();
        	String sapFieldName = entry.getValue();
        	
            Object sapFieldValue = src.getValue(sapFieldName);
            try {
                setSimpleProperty(des, beanPropertyName, sapFieldValue);
            } catch (Exception e) {
            	PropertyDescriptor pd = getPropertyDescriptor(des, beanPropertyName);
            	if(boolean.class.equals(pd.getPropertyType())){
            		
            	}
                Method method = getPropertyDescriptor(des, beanPropertyName).getWriteMethod();
                if (method == null) {
                    continue;
                }
                method = des.getClass().getMethod(method.getName(), sapFieldValue.getClass());
                method.invoke(des, sapFieldValue);
            }
        }
    }
    
    public static void fill(JCoRecord des, Object src) throws Exception {
        for (Entry<String, String> entry : cache.get(src.getClass()).entrySet()) {
        	String beanPropertyName = entry.getKey();
        	String sapFieldName = entry.getValue();
        	
        	Object beanPropertyValue = getSimpleProperty(src, beanPropertyName);
        	if(beanPropertyValue instanceof Boolean){
				des.setValue(sapFieldName, ((Boolean) beanPropertyValue) ? "X" : "-");
        	} else {
        		des.setValue(sapFieldName, beanPropertyValue);
        	}
        }
    }

    private static final LoadingCache<Class<?>, Map<String, String>> cache = CacheBuilder.newBuilder().build(
            new CacheLoader<Class<?>, Map<String, String>>() {

                @Override
                public Map<String, String> load(Class<?> clazz) throws Exception {
                    ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
                    for (Field field : clazz.getDeclaredFields()) {
                        if (isFinal(field.getModifiers()) || isStatic(field.getModifiers()) || field.getAnnotation(SapTransient.class) != null) {
                            continue;
                        }
                        SapConvertField scf = field.getAnnotation(SapConvertField.class);
                        if (scf == null) {
                            builder.put(field.getName(), upperCase(field.getName()));
                        } else {
                            builder.put(field.getName(), scf.value());
                        }
                    }
                    return builder.build();
                }

            });
}
