package com.duke.xlws.circle.bean;

import java.io.Serializable;

/**
 * 创建：duke
 * 注释：
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/26.
 */


public class FcLikeBean implements Serializable{
    public String fcId;//朋友圈ID
    public String fcLikeId;//点赞ID
    public String toLikeUserId; //被点赞人的ID
    public String toLikeUserName;//被点赞人的昵称
    public String fromUserId; //点赞人的ID
    public String fromUserName;//点赞人的名称

    @Override
    public String toString() {
        return "FcLikeBean{" +
                "fcId='" + fcId + '\'' +
                ", fcLikeId='" + fcLikeId + '\'' +
                ", toLikeUserId='" + toLikeUserId + '\'' +
                ", toLikeUserName='" + toLikeUserName + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                '}';
    }
}
