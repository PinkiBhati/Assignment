package com.Rest.RestfulDemo.Extras;
public class HelloWorldBean {

       private  String msg;

    public HelloWorldBean()
    {
        System.out.println("hello1");
    }

    public HelloWorldBean(String msg) {
        this.msg=msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "HelloWorldBean{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
