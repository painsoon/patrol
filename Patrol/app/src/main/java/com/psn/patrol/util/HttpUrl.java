package com.psn.patrol.util;

/**
 * Author: shinianPan on 2017/4/10.
 * email : snow_psn@163.com
 */

public class HttpUrl {
    /*
    *巡逻员端
     */
    public static final String url_submitResult = "http://121.41.48.225:8080/daoyuan_eagle/eagle/checkResult/submitResult.do";
    //提交异常信息
    //public static final String url_exceptionInfo = "http://192.168.1.100:8080/daoyuan_eagle/eagle/abnormal/abnormalexcepUpload.do";
    public static final String url_exceptionInfo = "http://121.41.48.225:8080/daoyuan_eagle/eagle/abnormal/abnormalexcepUpload.do";
    public static final String url_pathTest = "http://121.41.48.225:8080/daoyuan_eagle/eagle/path/pathTest.do";
    //public static final String url_pathTest = "http://192.168.1.100:8080/daoyuan_eagle/eagle/path/pathTest.do";
    public static final String url_login = "http://121.41.48.225:8080/daoyuan_eagle/eagle/user/alogin.do";
    //public static final String url_login = "http://192.168.1.100:8080/daoyuan_eagle//eagle/user/alogin.do";


    /*
    *android
    * 管理员端
     */
    public static final String url_addPathInfo = "http://121.41.48.225:8080/daoyuan_eagle/eagle/path/addByApp.do";
   // public static final String url_addPathInfo = "http://192.168.1.100:8080/daoyuan_eagle/eagle/path/addByApp.do";
    //查询未分配的路线
    public static final String url_pathall = "http://121.41.48.225:8080/daoyuan_eagle/eagle/path/unAssignpath_byAndroid.do";

    //变更顺序
    public static final String url_pathOrder = "http://121.41.48.225:8080/daoyuan_eagle/eagle/path/list_byAndroid.do";
    public static final String url_spotList = "http://121.41.48.225:8080/daoyuan_eagle/eagle/path/spotList_byAndroid.do";
    public static final String url_updata = "http://121.41.48.225:8080/daoyuan_eagle/eagle/path/updata.do";


    //将选中的路径分配给员工
    public static final String url_Assignpath = "http://121.41.48.225:8080/daoyuan_eagle/eagle/task/Assignpath.do";

    public static final String url_puser = "http://121.41.48.225:8080/daoyuan_eagle/eagle/user/puser.do";

    //查询检查情况
    public static final String url_PathInfo = "http://121.41.48.225:8080/daoyuan_eagle/eagle/checkResult/listByAndroid.do";
    //查询异常信息
    public static final String url_ExceptionInfo = "http://121.41.48.225:8080/daoyuan_eagle/eagle/abnormal/listByAndroid.do";
    //查询员工
    public static final String url_userlist = "http://121.41.48.225:8080/daoyuan_eagle/eagle/userManage/user_list.do";
    //增加员工
    public static final String url_add_bya = "http://121.41.48.225:8080/daoyuan_eagle/eagle/userManage/add_bya.do";
    //删除员工
    public static final String url_userdelete = "http://121.41.48.225:8080/daoyuan_eagle/eagle/userManage/userdelete.do";
}
