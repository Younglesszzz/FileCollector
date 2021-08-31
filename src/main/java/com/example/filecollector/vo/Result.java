package com.example.filecollector.vo;

public class Result {

    Object data;

    String msg;

    StringBuffer url;

    //正常类型的包装返回的结果
    public Result(Object data, String msg) {
        this.setData(data);
        this.setMsg(msg);
    }

    public Result() {

    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StringBuffer getUrl() {
        return url;
    }

    public void setUrl(StringBuffer url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", url=" + url +
                '}';
    }

}