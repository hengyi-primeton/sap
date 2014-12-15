/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hengyi.japp.sap.convert.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hengyi.japp.sap.annotation.SapConvertField;
import com.hengyi.japp.sap.annotation.SapTransient;
import com.hengyi.japp.sap.convert.IFieldCopy;
import com.hengyi.japp.sap.convert.ISapConvert;
import com.sap.conn.jco.JCoMetaData;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author jzb
 */
public class SapConverts {

	private Class<?> beanClass;
	private JCoMetaData metaData;

	public static SapConverts begin() {
		return new SapConverts();
	}

	public ISapConvert build() throws Exception {
		if (beanClass == null) {
			throw new IllegalArgumentException("beanClass have no value");
		}
		if (metaData == null) {
			throw new IllegalArgumentException("JCoMetaData have no value");
		}
		ISapConvert sapConvert = cache.get(beanClass).get(metaData);
		if (sapConvert != null) {
			return sapConvert;
		}
		synchronized (beanClass) {
			sapConvert = cache.get(beanClass).get(metaData);
			if (sapConvert != null) {
				return sapConvert;
			}
			sapConvert = newInstance();
			if (sapConvert == null) {
				return null;
			}
			cache.get(beanClass).put(metaData, sapConvert);
		}
		return sapConvert;
	}

	private ISapConvert newInstance() throws Exception {
		ImmutableSet.Builder<IFieldCopy> fieldCoyps = ImmutableSet.builder();
		PropertyDescriptor[] descriptors = PropertyUtils
				.getPropertyDescriptors(beanClass);
		if (descriptors == null) {
			return null;
		}
		for (PropertyDescriptor descriptor : descriptors) {
			String sapFieldName = getSapFieldName(descriptor);
			if (sapFieldName == null) {
				continue;
			}

			IFieldCopy fieldCopy = FieldCopys.begin().addBeanClass(beanClass)
					.addBeanPropertyDescriptor(descriptor)
					.addRecordMetaData(metaData).addSapFieldName(sapFieldName)
					.build();
			if (fieldCopy != null) {
				fieldCoyps.add(fieldCopy);
			}
		}
		return new SapConvert(fieldCoyps.build());
	}

	private String getSapFieldName(PropertyDescriptor descriptor) {
		Method readMethod = descriptor.getReadMethod();
		SapConvertField scf = readMethod.getAnnotation(SapConvertField.class);
		if (scf != null) {
			return StringUtils.upperCase(scf.value());
		}
		if (readMethod.getAnnotation(SapTransient.class) != null) {
			return null;
		}

		String beanPropertyName = descriptor.getName();
		for (Field field : beanClass.getDeclaredFields()) {
			if (!field.getName().equals(beanPropertyName)) {
				continue;
			}

			if (field.getAnnotation(SapTransient.class) != null) {
				return null;
			}
			scf = field.getAnnotation(SapConvertField.class);
			if (scf != null) {
				return StringUtils.upperCase(scf.value());
			}
		}
		return StringUtils.upperCase(beanPropertyName);
	}

	public SapConverts addBean(Object bean) {
		this.beanClass = bean.getClass();
		return this;
	}

	public SapConverts addBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
		return this;
	}

	public SapConverts addRecord(JCoRecord record) {
		this.metaData = record.getMetaData();
		return this;
	}

	public SapConverts addRecordMetaData(JCoMetaData metaData) {
		this.metaData = metaData;
		return this;
	}

	private SapConverts() {
	}

	private static final LoadingCache<Class<?>, Map<JCoMetaData, ISapConvert>> cache = CacheBuilder
			.newBuilder().build(
					new CacheLoader<Class<?>, Map<JCoMetaData, ISapConvert>>() {

						@Override
						public Map<JCoMetaData, ISapConvert> load(Class<?> key)
								throws Exception {
							return Maps.newConcurrentMap();
						}

					});

	private class SapConvert implements ISapConvert {

		private final Iterable<IFieldCopy> fieldCopys;

		private SapConvert(Iterable<IFieldCopy> fieldCopys) {
			this.fieldCopys = fieldCopys;
		}

//		@Override
		public <T> T convert(Class<T> clazz, JCoRecord record) throws Exception {
			T result = clazz.newInstance();
			copy(result, record);
			return result;
		}

//		@Override
		public <T> List<T> convert(Class<T> clazz, JCoTable table)
				throws Exception {
			final int numRows = table.getNumRows();
			List<T> result = Lists.newArrayListWithExpectedSize(numRows);
			for (int i = 0; i < numRows; i++) {
				table.setRow(i);
				JCoRecord record = table;
				result.add(convert(clazz, record));
			}
			return result;
		}

//		@Override
		public void copy(Object bean, JCoRecord record) throws Exception {
			for (IFieldCopy fieldCopy : fieldCopys) {
				fieldCopy.copy(bean, record);
			}
		}

//		@Override
		public void copy(JCoRecord record, Object bean) throws Exception {
			for (IFieldCopy fieldCopy : fieldCopys) {
				fieldCopy.copy(record, bean);
			}
		}

		@Override
		public String toString() {
			return fieldCopys == null ? null : fieldCopys.toString();
		}
	}
}
