package com.liuye.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip, boolean... canNull) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (canNull!=null && canNull.length>0 && canNull[0]) {
            return scanner.nextLine();
        } else {
            if (scanner.hasNext()) {
                String ipt = scanner.next();
                if (StringUtils.isNotEmpty(ipt)) {
                    return ipt;
                }
            }
        }

        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }





    public static void main(String[] args) {

        // 输入参数
        System.out.println("------ 请输入项目、模块相关参数 ------");
        String moudleproject = scanner("代码在哪个子项目中生成？请指定子项目名");//"wyzdz";
        String moudlename = scanner("模块名");//"wyzdz";

        System.out.println("------ 请输入数据表相关参数 ------");
        String tablename = scanner("表名（<数据库>.<用户>.<表>）");//"enterprise_service.dbo.T_DisChargeMonitor_SignIn";
//        String[] tableprefixes = tablename.split("\\.", 3);
//        if (tableprefixes==null || tableprefixes.length!=3)
//            throw new MybatisPlusException("请输入正确的表名！");
//        String databasename = tableprefixes[0];
//        tablename = tableprefixes[2];
//        if (StringUtils.isBlank(tablename)) {
//            throw new MybatisPlusException("请输入正确的表名！");
//        }
        String deletefieldname = null;
        deletefieldname = scanner("逻辑删除字段（没有可不填）", true);//"is_deleted";
        final String createUserIdFieldname = scanner("创建人字段（没有可不填）", true);//"Oper_Person";
        final String createTimeFieldname = scanner("创建时间字段（没有可不填）", true);//"Oper_Time";
        final String updateUserIdFieldname = scanner("修改人字段（没有可不填）", true);//"";
        final String updateTimeFieldname = scanner("修改时间字段（没有可不填）", true);//"";

        System.out.println("------ 请输入页面相关参数 ------");
        Map<String, String> queryFieldNames = new HashMap<String, String>();
        String[] queryFieldNamees = scanner("查询条件用到的字段（逗号分隔）").split(",");//"OnLine_Device_Id,fault_equipment_name".split(",");
        for (String queryFieldName: queryFieldNamees) {
            if (StringUtils.isNotBlank(queryFieldName)) {
                queryFieldNames.put(queryFieldName, queryFieldName);
            }
        }
        Map<String, String> listFieldNames = new HashMap<String, String>();
        String[] listFieldNamees = scanner("列表中用到的字段（逗号分隔）").split(",");//"OnLine_Device_Id,fault_equipment_name,fault_time,work_time".split(",");
        for (String listFieldName: listFieldNamees) {
            if (StringUtils.isNotBlank(listFieldName)) {
                listFieldNames.put(listFieldName, listFieldName);
            }
        }

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/" + moudleproject + "/src/main/java");
        gc.setAuthor("orange");
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/wyzdz");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moudlename);
        pc.setParent("com.liuye");
        pc.setEntity("entities");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String, Object> map = this.getMap();
                if (map==null) {
                    map = new HashMap<String, Object>();
                }
                map.put("queryFieldNames", queryFieldNames);
                map.put("listFieldNames", listFieldNames);
                map.put("tablePrefixes", tablename);
                map.put("createUserIdFieldName", StringUtils.isNotBlank(createUserIdFieldname) ? createUserIdFieldname : "");
                map.put("createTimeFieldName", StringUtils.isNotBlank(createTimeFieldname) ? createTimeFieldname : "");
                map.put("updateUserIdFieldName", StringUtils.isNotBlank(updateUserIdFieldname) ? updateUserIdFieldname : "");
                map.put("updateTimeFieldName", StringUtils.isNotBlank(updateTimeFieldname) ? updateTimeFieldname : "");
                setMap(map);
            }
        };

        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/codegen/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/" + moudleproject + "/src/main/resources/mybatis/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
//        // list.html
//        String listTemplatePath = "/codegen/templates/list.html.vm";
//        focList.add(new FileOutConfig(listTemplatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + moudleproject + "/src/main/resources/templates/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "/list.html";
//            }
//        });
//        // input.html
//        String inputTemplatePath = "/codegen/templates/input.html.vm";
//        focList.add(new FileOutConfig(inputTemplatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + moudleproject + "/src/main/resources/templates/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "/input.html";
//            }
//        });
//        // show.html
//        String showTemplatePath = "/codegen/templates/show.html.vm";
//        focList.add(new FileOutConfig(showTemplatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/" + moudleproject + "/src/main/resources/templates/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "/show.html";
//            }
//        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("/codegen/templates/entity.java");
        templateConfig.setService("/codegen/templates/service.java");
        templateConfig.setServiceImpl("/codegen/templates/serviceImpl.java");
        templateConfig.setController("/codegen/templates/controller.java");
        //templateConfig.setXml("/codegen/templates/mapper.xml");
        templateConfig.setMapper("/codegen/templates/mapper.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.liuye.common.entities.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(false);
        strategy.setSuperControllerClass("com.liuye.common.controller.BaseController");
        strategy.setInclude(tablename);
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(false);
        if (StringUtils.isNotBlank(deletefieldname)) {
            strategy.setLogicDeleteFieldName(deletefieldname);
        }
        //strategy.setTablePrefix("T_");
        strategy.setSuperServiceImplClass("com.liuye.common.service.BaseService");
        mpg.setStrategy(strategy);
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
