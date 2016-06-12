package com.favorite.wick.parcelable;

/**
 * Created by Weimin on 6/1/2016.
 */
public class Modle {

    /**
     * informationId : 2
     * title : 创业想法成功的5个步骤
     * brief : http://static.zixun.9978.cn/Upload/image/20140228/20140228084847_81085.jpg
     * content :     现在，
     * publishTime : 2015-08-05 21:42:16
     * category : null
     * viewNum : 26
     * favoriteNum : 0
     * voteUp : 0
     * voteDown : 0
     * isFavorited : 0
     * isVoted : 0
     */

    private String informationId;
    private String title;
    private String brief;
    private String content;
    private String publishTime;
    private Object category;
    private String viewNum;
    private String favoriteNum;
    private String voteUp;
    private String voteDown;
    private String isFavorited;
    private String isVoted;

    public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public String getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(String favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public String getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(String voteUp) {
        this.voteUp = voteUp;
    }

    public String getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(String voteDown) {
        this.voteDown = voteDown;
    }

    public String getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(String isFavorited) {
        this.isFavorited = isFavorited;
    }

    public String getIsVoted() {
        return isVoted;
    }

    public void setIsVoted(String isVoted) {
        this.isVoted = isVoted;
    }
}
