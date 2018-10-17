package cn.org.zhixiang.entity;

import java.util.Map;

/**
 * describe:
 *
 * @author syj
 * CreateTime 2018/09/30
 * Description 分页查询对象
 */
public class GridPageRequest {
    /**
     * 查询关键字Map
     */
    private Map<String, String> searchMap;
    /**
     * 模糊查询关键字Map
     */
    private Map<String, String> likeSearchMap;
    /**
     * 排序关键字Map
     */
    private Map<String, String> orderMap;
    /**
     * 分组关键字数组
     */
    private String[] groupArray;
    /**
     * 第几页
     */
    private Integer pageNum;
    /**
     * 查几条
     */
    private Integer pageSize;
    /**
     * 大于等于查询关键字Map
     */
    private Map<String, String> largeSearchMap;
    /**
     * 小于等于查询关键字Map
     */
    private Map<String, String> smallSearchMap;
    /**
     * 不等于查询关键字Map
     */
    private Map<String, String> notEquleSearchMap;

    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, String> searchMap) {
        this.searchMap = searchMap;
    }

    public Map<String, String> getLikeSearchMap() {
        return likeSearchMap;
    }

    public void setLikeSearchMap(Map<String, String> likeSearchMap) {
        this.likeSearchMap = likeSearchMap;
    }

    public Map<String, String> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<String, String> orderMap) {
        this.orderMap = orderMap;
    }

    public String[] getGroupArray() {
        return groupArray;
    }

    public void setGroupArray(String[] groupArray) {
        this.groupArray = groupArray;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, String> getLargeSearchMap() {
        return largeSearchMap;
    }

    public void setLargeSearchMap(Map<String, String> largeSearchMap) {
        this.largeSearchMap = largeSearchMap;
    }

    public Map<String, String> getSmallSearchMap() {
        return smallSearchMap;
    }

    public void setSmallSearchMap(Map<String, String> smallSearchMap) {
        this.smallSearchMap = smallSearchMap;
    }

    public Map<String, String> getNotEquleSearchMap() {
        return notEquleSearchMap;
    }

    public void setNotEquleSearchMap(Map<String, String> notEquleSearchMap) {
        this.notEquleSearchMap = notEquleSearchMap;
    }
}
