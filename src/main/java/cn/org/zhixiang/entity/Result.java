package cn.org.zhixiang.entity;

import org.springframework.http.HttpStatus;

/**
 * d
 *
 * @author syj
 * CreateTime 2018/10/09
 * describe: controller返回结果包装类
 */
public class Result {
    /**
     * 返回值
     */
    private int returnCode = 200;
    /**
     * 提示信息
     */
    private String returnMsg = "操作成功";
    /**
     * 返回数据
     */
    private Object returnData;
    /**
     * 构造方法
     */
    public Result() {
        this.returnCode = HttpStatus.OK.value();
        this.returnMsg = "操作成功";
    }
    public Result(Object returnData) {
        this.returnCode = HttpStatus.OK.value();
        this.returnMsg = "操作成功";
        this.returnData = returnData;
    }

    public Result(int returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public Result(int returnCode, String returnMsg, Object returnData) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.returnData = returnData;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }
}
