package com.example.mrz.newproject.model.bean;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Mr.z on 2017/9/12.
 */

public class UrlBean {

    //ip地址
    public final static String IP = "http://222.180.192.2:8918";

    //链接中的sessionI
    public static String sessionId;

    //发起登录请求的地址后缀
    public static String loginUrl = "default4.aspx";

    //教务系统的首页地址后缀
    public static String mainUrl = "xs_main.aspx";

    //全校选课
    public static String schoolCode = "N121106";
    public static String selectAllSchoolUrl= "xf_xsqxxxk.aspx";


    //学院选课
    public static String academyCode = "N121110";
    public static String selectElectiveUrl= "xf_xsyxxxk.aspx";

    //用户信息
    public static String userInfoCode = "N121501";
    public static String userInfoUrl = "xsgrxx.aspx";

    //课表信息
    public static String courseUrl = "xskbcx.aspx";
    public static String courseCode ="N121603";

    //一卡通登录地址
    public final static String ECARDURL = "http://ecard.cqcet.edu.cn/";

    //一卡通余额查询
    public final static String CARDHOLDER_URL = "http://ecard.cqcet.edu.cn/Cardholder/AccBalance.aspx";

    //一卡通消费记录查询
    public final static String ECARD_QUERY_URL = "http://ecard.cqcet.edu.cn/Cardholder/Queryhistory.aspx";
    //成绩查询
    public static String scoreUrl = "xscjcx.aspx";
    public static String scoreCode = "N121605";



    public static void setSessionId(String sessionId) {
        UrlBean.sessionId = sessionId;
    }

    /**
     * 将键值对形式的键值对数据转换为链接形式
     *
     * @param postDatas 需要提交的表单数据
     * @return
     */
    public static String utf2Gbk(Map<String,String> postDatas){
        String parma = "";

        for (Iterator<Map.Entry<String,String>> it = postDatas.entrySet().iterator(); it.hasNext();){
            Map.Entry entry = it.next();
            parma += entry.getKey() + "=" + entry.getValue();
            if(it.hasNext())
                parma += "&";
        }
        return parma;
    }

}
