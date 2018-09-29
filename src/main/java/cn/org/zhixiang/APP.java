package cn.org.zhixiang;

import cn.org.zhixiang.annotation.SyjController;

@SyjController
public class APP {
    private String value;

    private String value2;

    public APP(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        APP app = new APP("it works");
        System.out.println(app.getValue());
    }
}
