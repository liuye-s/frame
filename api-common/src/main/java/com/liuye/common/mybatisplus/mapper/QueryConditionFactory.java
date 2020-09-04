package com.liuye.common.mybatisplus.mapper;

import com.liuye.common.util.beans.SpringContextUtil;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.lang.reflect.Modifier;
import java.util.*;



public class QueryConditionFactory {

	protected final static Log logger = LogFactory.getLog(QueryConditionFactory.class);
	
	private static boolean initFlag = false;
	private static Map<String, List<IQueryCondition>> map = new HashMap<String, List<IQueryCondition>>();
	
	private volatile static QueryConditionFactory instance = null;
	
	private QueryConditionFactory() {
		
	}
	
	public static QueryConditionFactory getInstance() {
		if (instance==null) {
			synchronized (QueryConditionFactory.class) {
				if (instance==null) {
					instance = new QueryConditionFactory();
				}
			}
		}
		
		return instance;
	}

	private boolean init() {
		if (!initFlag) {
			synchronized (QueryConditionFactory.class) {
				if (!initFlag) {
					try {
						//ClassLoaderUtil.loadClassesFromPath();

						//List<Class<?>> list = ClassLoaderUtil.getSubClasses(IQueryCondition.class);
						//for (Class<?> clazz : list) {
						Map<String, IQueryCondition> beanMap = SpringContextUtil.getBeansOfType(IQueryCondition.class);
						for (IQueryCondition entity : beanMap.values()) {

							Class<?> clazz = entity.getClass();

							int modifiers = clazz.getModifiers();
							if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers))
								continue;
							try {
								//IQueryCondition entity = (IQueryCondition) SpringContextUtil.getBean(StringUtils.uncapitalise(clazz.getSimpleName()));
								List<IQueryCondition> entityList;
								if (!map.containsKey(entity.getQueryType())) {
									entityList = new ArrayList<IQueryCondition>();
								} else {
									entityList = map.get(entity.getQueryType());
								}
								entityList.add(entity);
								
								// 若有多个逻辑实现类，则先排序
								if (entityList.size()>1)
									Collections.sort(entityList, new QueryConditionComparator());
								
								map.put(entity.getQueryType(), entityList);
							} catch (Exception ex) {
								ex.printStackTrace();
								logger.error("页面自动查询条件处理类实例化错误", ex);
							}
						}
						
						initFlag = true;
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("页面自动查询条件处理类读取失败", e);
					}
				}
			}
		}
		return initFlag;
	}

	public List<IQueryCondition> getQueryConditionList(String queryType) {
		if (!initFlag)
			init();
		
		if (map.containsKey(queryType)) {
			return map.get(queryType);
		} else {
			return null;
		}
	}

}
