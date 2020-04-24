package com.meituan.android.uitool.biz.mock;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/9 on 上午10:55
 */
public class PxeMockBean {
    public DataBean data;

    public static class DataBean {
        @SerializedName("tips")
        public String tips;
        @SerializedName("mockData")
        public List<MockDataBean> mockData;
    }

    public static class MockDataBean {
        public String version;
        @SerializedName("case")
        public List<CaseBean> caseX;
    }

    public static class CaseBean {
        public String mockId;
        public String des;
        public List<MockDetailBean> mockDetail;
    }

    public static class MockDetailBean {
        public String apiPath;
        public List<String> details;
    }
}
