package com.liuye.common.mybatisplus.noahsql;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.extension.injector.methods.*;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class NoahSqlInjector extends AbstractSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList() {
        return Stream.of(
                new InsertBatch(),
                new Insert(),
                /*new Delete(),
                new DeleteByMap(),
                new DeleteById(),
                new DeleteBatchByIds(),
                new Update(),
                new UpdateById(),
                new SelectById(),
                new SelectBatchByIds(),
                new SelectByMap(),
                new SelectOne(),
                new SelectCount(),
                new SelectMaps(),
                new SelectMapsPage(),
                new SelectObjs(),
                new SelectList(),
                new SelectPage()*/
                new LogicDelete(),
                new LogicDeleteByMap(),
                new LogicDeleteById(),
                new LogicDeleteBatchByIds(),
                new LogicUpdate(),
                new LogicUpdateById(),
                new LogicSelectById(),
                new LogicSelectBatchByIds(),
                new LogicSelectByMap(),
                new LogicSelectOne(),
                new LogicSelectCount(),
                new LogicSelectMaps(),
                new LogicSelectMapsPage(),
                new LogicSelectObjs(),
                new LogicSelectList(),
                new LogicSelectPage()
        ).collect(toList());
    }
}
