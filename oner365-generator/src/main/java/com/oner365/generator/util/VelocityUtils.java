package com.oner365.generator.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.constants.PublicConstants;
import com.oner365.generator.constants.GenConstants;
import com.oner365.generator.entity.GenTable;
import com.oner365.generator.entity.GenTableColumn;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * 模板处理工具类
 *
 * @author zhaoyong
 */
public class VelocityUtils {
    /** 项目空间路径 */
    private static final String PROJECT_PATH = "main/java";

    /** mybatis空间路径 */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /** 默认上级菜单，系统工具 */
    private static final String DEFAULT_PARENT_MENU_ID = "3";

    private static final String ENTITY_JAVA_VM = "entity.java.vm";
    private static final String VO_JAVA_VM = "vo.java.vm";
    private static final String DTO_JAVA_VM = "dto.java.vm";
    private static final String MAPPER_JAVA_VM = "mapper.java.vm";
    private static final String DAO_JAVA_VM = "dao.java.vm";
    private static final String SERVICE_JAVA_VM = "service.java.vm";
    private static final String SERVICE_IMPL_JAVA_VM = "serviceImpl.java.vm";
    private static final String CONTROLLER_JAVA_VM = "controller.java.vm";
    private static final String MAPPER_XML_VM = "mapper.xml.vm";
    private static final String SQL_VM = "sql.vm";
    private static final String API_JS_VM = "api.js.vm";
    private static final String INDEX_VUE_VM = "index.vue.vm";
    private static final String INDEX_TREE_VUE_VM = "index-tree.vue.vm";

    /** 构造方法 */
    private VelocityUtils() {
        super();
    }

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTable genTable) {
        String moduleName = genTable.getModuleName();
        String businessName = genTable.getBusinessName();
        String packageName = genTable.getPackageName();
        String tplCategory = genTable.getTplCategory();
        String functionName = genTable.getFunctionName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTplCategory());
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", !DataUtils.isEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("BusinessName", StringUtils.capitalize(genTable.getBusinessName()));
        velocityContext.put("businessName", genTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("datetime", DateUtil.getCurrentDate());
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable.getColumns()));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("table", genTable);
        setMenuVelocityContext(velocityContext, genTable);
        if (GenConstants.TPL_TREE.equals(tplCategory)) {
            setTreeVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }

    public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String parentMenuId = getParentMenuId(paramsObj);
        context.put("parentMenuId", parentMenuId);
    }

    public static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            context.put("tree_parent_code", paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            context.put("tree_name", paramsObj.getString(GenConstants.TREE_NAME));
        }
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory) {
        List<String> templates = new ArrayList<>();

        // java code
        templates.add("vm/java/entity.java.vm");
        templates.add("vm/java/vo.java.vm");
        templates.add("vm/java/dto.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/dao.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");

        // sql scripts
        templates.add("vm/sql/sql.vm");

        // vue code
        templates.add("vm/js/api.js.vm");
        if (GenConstants.TPL_CRUD.equals(tplCategory)) {
            templates.add("vm/vue/index.vue.vm");
        } else if (GenConstants.TPL_TREE.equals(tplCategory)) {
            templates.add("vm/vue/index-tree.vue.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTable genTable) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();
        // 模块名
        String moduleName = genTable.getModuleName();
        // 大写类名
        String className = genTable.getClassName();
        // 业务名称
        String businessName = genTable.getBusinessName();

        String javaPath = PROJECT_PATH + PublicConstants.DELIMITER + StringUtils.replace(packageName, ".", PublicConstants.DELIMITER);
        String mybatisPath = MYBATIS_PATH + PublicConstants.DELIMITER + moduleName;
        String vuePath = "vue";

        if (template.contains(ENTITY_JAVA_VM)) {
            fileName = DataUtils.format("{}/entity/{}.java", javaPath, className);
        } else if (template.contains(VO_JAVA_VM)) {
            fileName = DataUtils.format("{}/vo/{}Vo.java", javaPath, className);
        } else if (template.contains(DTO_JAVA_VM)) {
          fileName = DataUtils.format("{}/dto/{}Dto.java", javaPath, className);
        } else if (template.contains(MAPPER_JAVA_VM)) {
            fileName = DataUtils.format("{}/mapper/{}Mapper.java", javaPath, className);
        } else if (template.contains(DAO_JAVA_VM)) {
            fileName = DataUtils.format("{}/dao/I{}Dao.java", javaPath, className);
        } else if (template.contains(SERVICE_JAVA_VM)) {
            fileName = DataUtils.format("{}/service/I{}Service.java", javaPath, className);
        } else if (template.contains(SERVICE_IMPL_JAVA_VM)) {
            fileName = DataUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains(CONTROLLER_JAVA_VM)) {
            fileName = DataUtils.format("{}/controller/{}Controller.java", javaPath, className);
        } else if (template.contains(MAPPER_XML_VM)) {
            fileName = DataUtils.format("{}/{}Mapper.xml", mybatisPath, className);
        } else if (template.contains(SQL_VM)) {
            fileName = businessName + "Menu.sql";
        } else if (template.contains(API_JS_VM)) {
            fileName = DataUtils.format("{}/api/{}/{}.js", vuePath, moduleName, businessName);
        } else if (template.contains(INDEX_VUE_VM)) {
            fileName = DataUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        } else if (template.contains(INDEX_TREE_VUE_VM)) {
            fileName = DataUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
        }
        return fileName;
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf('.');
        return StringUtils.substring(packageName, 0, lastIndex);
    }

    /**
     * 根据列类型获取导入包
     *
     * @param columns 列集合
     * @return 返回需要导入的包列表
     */
    public static Set<String> getImportList(List<GenTableColumn> columns) {
        Set<String> importList = new HashSet<>();
        for (GenTableColumn column : columns) {
            if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType())) {
                importList.add("java.util.LocalDate");
            } else if (!column.isSuperColumn() && GenConstants.TYPE_DATE_TIME.equals(column.getJavaType())) {
                importList.add("java.util.LocalDateTime");
            } else if (!column.isSuperColumn() && GenConstants.TYPE_BIG_DECIMAL.equals(column.getJavaType())) {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName   模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName) {
        return DataUtils.format("{}:{}", moduleName, businessName);
    }

    /**
     * 获取上级菜单ID字段
     *
     * @param paramsObj 生成其他选项
     * @return 上级菜单ID字段
     */
    public static String getParentMenuId(JSONObject paramsObj) {
        if (!DataUtils.isEmpty(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID)) {
            return paramsObj.getString(GenConstants.PARENT_MENU_ID);
        }
        return DEFAULT_PARENT_MENU_ID;
    }

    /**
     * 获取树编码
     *
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_CODE)) {
            return DataUtils.coderName(paramsObj.getString(GenConstants.TREE_CODE));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树父编码
     *
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
            return DataUtils.coderName(paramsObj.getString(GenConstants.TREE_PARENT_CODE));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树名称
     *
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(JSONObject paramsObj) {
        if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
            return DataUtils.coderName(paramsObj.getString(GenConstants.TREE_NAME));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GenTable genTable) {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String treeName = paramsObj.getString(GenConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumn column : genTable.getColumns()) {
            if (column.isList()) {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName)) {
                    break;
                }
            }
        }
        return num;
    }
}
