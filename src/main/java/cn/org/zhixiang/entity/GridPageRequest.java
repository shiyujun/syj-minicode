package cn.org.zhixiang.entity;

import java.util.Map;

/**
 * describe:
 *
 * @创建人 syj
 * @创建时间 2018/09/30
 * @描述 分页查询对象
 */
public class GridPageRequest {
    /**
     * 查询关键字
     */
    private Map<String, String> searchMap;
    /**
     * 模糊查询关键字
     */
    private Map<String, String> likeSearchMap;
    /**
     * 排序关键字
     */
    private Map<String, String> orderMap;
    /**
     * 分组关键字
     */
    private String[] groupArray;

    private int pageNum;
    private int pageSize;


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

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
