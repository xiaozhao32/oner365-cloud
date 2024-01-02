package com.oner365.sys.constants;

import com.oner365.data.commons.constants.PublicConstants;

/**
 * 系统用户常量
 * @author zhaoyong
 */
public class SysConstants extends PublicConstants {
    
    public static final String ID = "id";
    public static final String USER_ID = "userId";
    public static final String ROLE_ID = "roleId";
    
    public static final String STATUS = "status";
    public static final String CODE = "code";
    public static final String USER_NAME = "userName";
    public static final String PASS = "password";

    /**
     * 密码缩写
     */
    public static final String P = "p";
    
    public static final String REAL_NAME = "realName";
    public static final String ROLE_NAME = "roleName";
    public static final String IS_ADMIN = "isAdmin";
    public static final String AVATAR = "avatar";
    public static final String CREATE_TIME = "createTime";
    public static final String BEGIN_TIME = "beginTime";
    public static final String END_TIME = "endTime";
    
    public static final String USER_TYPE = "userType";
    public static final String SYS_ROLE = "sysRole";
    public static final String MENU_TYPE = "menuType";
    public static final String MENU_NAME = "menuName";
    public static final String MENU_TYPE_ID = "menuTypeId";
    public static final String MENU_ORDER = "menuOrder";
    public static final String EXPAND = "expand";
    public static final String CHILDREN = "children";
    public static final String PARENT_ID = "parentId";
    
    public static final String JOB_NAME = "jobName";
    public static final String JOB_ORDER = "jobOrder";
    
    public static final String TYPE_NAME = "typeName";
    public static final String TYPE_CODE = "typeCode";
    public static final String TYPE_ORDER = "typeOrder";
    public static final String TYPE_ID = "typeId";
    
    public static final String ITEM_NAME = "itemName";
    public static final String ITEM_CODE = "itemCode";
    public static final String ITEM_ORDER = "itemOrder";
    public static final String CAPTCHA_IMAGE = "CaptchaImage";
    public static final String OPERATION_TYPE = "operationType";
    
    public static final String ROLES = "roles";
    public static final String JOBS = "jobs";
    public static final String ORGS = "orgs";
    
    public static final String DEFAULT_ROLE = "1";
    public static final String DEFAULT_PARENT_ID = "-1";
    public static final String UUID = "uuid";
    
    
    /**
     * 任务服务名称
     */
    public static final String SCHEDULE_SERVER_NAME = "oner365-system";
    
    /**
     * Constructor
     */
    private SysConstants() {
        super();
    }
}
