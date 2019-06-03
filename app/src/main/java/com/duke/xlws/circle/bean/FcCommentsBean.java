package com.duke.xlws.circle.bean;

import java.io.Serializable;

/**
 * 创建：duke
 * 注释： 朋友圈评论
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/26.
 */


public class FcCommentsBean implements Serializable {
    public String fcId;//朋友圈ID
    public String commentsId;//评论人的ID
    public String fromUserId; //回复评论的id
    public String fromUserName;//回复评论的昵称
    public String toUserId;//被回复人的ID
    public String toUserName;//被回复人的昵称
    public String comments; //回复内容


    @Override
    public String toString() {
        return "FcCommentsBean{" +
                "fcId='" + fcId + '\'' +
                ", commentsId='" + commentsId + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
