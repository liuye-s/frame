package com.liuye.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuye.common.entities.FilterValueList;
import com.liuye.common.mybatisplus.mapper.IQueryCondition;
import com.liuye.common.mybatisplus.mapper.QueryConditionFactory;
import com.liuye.common.util.string.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public abstract class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Override
    public IPage<T> pageByFilter(IPage<T> page, Map<String, Object> data, String defaultTablePrefix) {
        Wrapper<T> wrapper = buildQueryCondition(data, defaultTablePrefix);
//        QueryWrapper<T> wrapper = new QueryWrapper<T>();
//        wrapper.eq("apply_persion", "46").or().eq("apply_persion", "46");
//        QueryWrapper<T> wrapper1 = new QueryWrapper<T>();
//        wrapper1.eq("apply_persion", "46").or().eq("apply_persion", "46");
//        QueryWrapper<T> condition = new QueryWrapper<T>();
//        condition.and(wp -> wp=wrapper);
        //condition.and(wp -> wp=wrapper1);
        //condition.and(wp -> wrapper).and(wp1 -> wrapper1);
        //condition.and(wp -> wp.eq("apply_persion", "46").or().eq("apply_persion", "46"))
        //.and(wp1 -> wp1.eq("apply_persion", "46").or().eq("apply_persion", "46"));
        return page(page, wrapper);
    }

    @Override
    public List<T> listByFilter(Map<String, Object> data, String defaultTablePrefix) {
        Wrapper wrapper = buildQueryCondition(data, defaultTablePrefix);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<T> listByFilter(Map<String, Object> data) {
        return listByFilter(data, null);
    }

    protected Wrapper buildQueryCondition(final Map<String, Object> data, String defaultTablePrefix) {
        return buildQueryCondition(data, "query.", defaultTablePrefix);
    }

    protected Wrapper<T> buildQueryCondition(final Map<String, Object> data, final String queryPrefix, String defaultTablePrefix) {
        QueryWrapper<T> condition = new QueryWrapper<T>();

        if (data!=null && data.size()>0) {

            Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String queryName = entry.getKey();

                if (StringUtils.isBlank(queryPrefix) || queryName.startsWith(queryPrefix)) {
                    if (StringUtils.isNotBlank(queryPrefix))
                        queryName = queryName.substring(queryPrefix.length());

                    List<String> queryNames = Arrays.asList(queryName.split("\\.or\\."));
                    if (queryNames==null || queryNames.size()<=0)
                        continue;

                    if (buildWrapperByData(new QueryWrapper<T>(), queryNames, entry.getValue(), entityClass, defaultTablePrefix)!=null)
                        condition.and(wrapper -> buildWrapperByData(wrapper, queryNames, entry.getValue(), entityClass, defaultTablePrefix));

                }

            }
        }

        return condition;
    }

    private QueryWrapper<T> buildWrapperByData(QueryWrapper<T> _condition, List<String> queryNames, Object value, Class <T> entityClass, String defaultTablePrefix) {
        boolean exists = false;
        boolean last_exists = false;

        for (int i=0; i<queryNames.size(); i++) {
            if (last_exists)
                _condition.or();

            last_exists = false;
            String queryName = queryNames.get(i);

            String[] params = queryName.split("[.]", 2);
            if (params==null || params.length<2)
                continue;
            QueryConditionFactory factory = QueryConditionFactory.getInstance();
            List<IQueryCondition> queryConditions = factory.getQueryConditionList(params[0]);
            if (queryConditions==null || queryConditions.size()<=0)
                continue;

            Object val;
            if (value instanceof FilterValueList) {
                try {
                    val = ((FilterValueList) value).get(i);
                } catch (Exception e) {
                    continue;
                }
            } else {
                val = value;
            }

            for (IQueryCondition queryCondition : queryConditions) {
                //if (queryCondition.addCondition(_condition, params[1], value, entityClass, defaultTablePrefix)) {
                if (queryCondition.addCondition(_condition, params[1], val, entityClass, defaultTablePrefix)) {
                    last_exists = true;
                    if (!exists) {
                        exists = true;
                    }
                }
            }
        }

        if (exists)
            return _condition;
        else
            return null;
    }

}
