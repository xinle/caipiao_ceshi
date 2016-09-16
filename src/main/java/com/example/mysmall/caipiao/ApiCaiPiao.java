package com.example.mysmall.caipiao;

import java.util.List;

/**
 * Created by danding on 2016/9/16.
 */
public class ApiCaiPiao {

    /**
     * rows : 20
     * code : ssq
     * info : 免费接口随机延迟3-5分钟，实时接口请访问opencai.net或QQ:9564384(注明彩票或API)
     * data : [{"expect":"2016108","opencode":"02,03,07,08,19,26+16","opentime":"2016-09-15 21:21:40","opentimestamp":1473945700},{"expect":"2016107","opencode":"06,11,18,26,27,32+01","opentime":"2016-09-13 21:21:40","opentimestamp":1473772900},{"expect":"2016106","opencode":"04,05,13,22,25,30+04","opentime":"2016-09-11 21:21:40","opentimestamp":1473600100},{"expect":"2016105","opencode":"08,10,19,27,28,31+16","opentime":"2016-09-08 21:21:40","opentimestamp":1473340900},{"expect":"2016104","opencode":"05,09,11,18,30,31+04","opentime":"2016-09-06 21:21:40","opentimestamp":1473168100},{"expect":"2016103","opencode":"01,05,13,19,24,27+11","opentime":"2016-09-04 21:21:40","opentimestamp":1472995300},{"expect":"2016102","opencode":"05,08,10,14,17,30+13","opentime":"2016-09-01 21:21:40","opentimestamp":1472736100},{"expect":"2016101","opencode":"01,03,19,24,32,33+01","opentime":"2016-08-30 21:21:40","opentimestamp":1472563300},{"expect":"2016100","opencode":"03,10,22,23,27,29+04","opentime":"2016-08-28 21:21:40","opentimestamp":1472390500},{"expect":"2016099","opencode":"01,11,21,23,27,33+06","opentime":"2016-08-25 21:21:40","opentimestamp":1472131300},{"expect":"2016098","opencode":"02,08,25,29,31,32+06","opentime":"2016-08-23 21:21:40","opentimestamp":1471958500},{"expect":"2016097","opencode":"06,13,25,26,28,31+01","opentime":"2016-08-21 21:21:40","opentimestamp":1471785700},{"expect":"2016096","opencode":"06,13,14,21,22,24+16","opentime":"2016-08-18 21:21:40","opentimestamp":1471526500},{"expect":"2016095","opencode":"01,05,09,12,18,32+12","opentime":"2016-08-16 21:21:40","opentimestamp":1471353700},{"expect":"2016094","opencode":"06,07,10,12,18,31+10","opentime":"2016-08-14 21:21:40","opentimestamp":1471180900},{"expect":"2016093","opencode":"06,09,15,17,25,27+09","opentime":"2016-08-11 21:21:40","opentimestamp":1470921700},{"expect":"2016092","opencode":"02,13,15,23,24,29+06","opentime":"2016-08-09 21:21:40","opentimestamp":1470748900},{"expect":"2016091","opencode":"04,08,14,22,23,28+07","opentime":"2016-08-07 21:21:40","opentimestamp":1470576100},{"expect":"2016090","opencode":"02,13,17,20,21,26+07","opentime":"2016-08-04 21:21:40","opentimestamp":1470316900},{"expect":"2016089","opencode":"01,03,14,30,31,32+08","opentime":"2016-08-02 21:21:40","opentimestamp":1470144100}]
     */

    private int rows;
    private String code;
    private String info;
    /**
     * expect : 2016108
     * opencode : 02,03,07,08,19,26+16
     * opentime : 2016-09-15 21:21:40
     * opentimestamp : 1473945700
     */

    private List<DataBean> data;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String expect;
        private String opencode;
        private String opentime;
        private int opentimestamp;

        public String getExpect() {
            return expect;
        }

        public void setExpect(String expect) {
            this.expect = expect;
        }

        public String getOpencode() {
            return opencode;
        }

        public void setOpencode(String opencode) {
            this.opencode = opencode;
        }

        public String getOpentime() {
            return opentime;
        }

        public void setOpentime(String opentime) {
            this.opentime = opentime;
        }

        public int getOpentimestamp() {
            return opentimestamp;
        }

        public void setOpentimestamp(int opentimestamp) {
            this.opentimestamp = opentimestamp;
        }
    }
}
