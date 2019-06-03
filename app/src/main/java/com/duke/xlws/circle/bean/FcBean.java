package com.duke.xlws.circle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建：duke
 * 注释：朋友圈 model
 * 版本大小：19
 * 版本名称：v2.3.0
 * 日期：2018/11/23.
 */


public class FcBean implements Serializable {
    public String fcId; //朋友圈ID
    public String userId; //用户ID
    //    public String headUrl; //用户头像
    public int headUrl; //用户头像
    public String nickName;//昵称
    public int fcType;    //0：文字   1：图片   2:文字和图片   3:HTML
    public String fcContentTxt;//发布的内容
    public String fcHtmlTxt;//发布网页的内容
    //    public String fcHtmlImage;//发布的网页的图片
    public int fcHtmlImage;//发布的网页的图片
    public String fcTime;//发布的时间
    public List<Integer> imageUrls;
    public List<FcLikeBean> mLikeList; //点赞
    public List<FcCommentsBean> mCommentsList;//评论


    @Override
    public String toString() {
        return "FcBean{" +
                "fcId='" + fcId + '\'' +
                ", userId='" + userId + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", fcType=" + fcType +
                ", fcContentTxt='" + fcContentTxt + '\'' +
                ", fcHtmlTxt='" + fcHtmlTxt + '\'' +
                ", fcHtmlImage='" + fcHtmlImage + '\'' +
                ", fcTime='" + fcTime + '\'' +
                ", mLikeList=" + toStringList(mLikeList) +
                ", mCommentsList=" + toStringList(mCommentsList) +
                '}';
    }


    private String toStringList(List<?> list) {
        if (mLikeList != null) {
            StringBuilder c = new StringBuilder("[ \n");
            for (int i = 0; i < list.size(); i++) {
                c.append(list.get(i).toString()).append("\n ,");
                if (i == list.size() - 1) {
                    c.append(" ]");
                }
            }

            return c.toString();
        }
        return "[ null ]";
    }


}
