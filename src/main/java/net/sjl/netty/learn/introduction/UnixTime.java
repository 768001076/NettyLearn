package net.sjl.netty.learn.introduction;

import java.util.Date;

/**
 * @Description: 时间工具类
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/16
 */
public class UnixTime {

    private long time;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long time) {
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override public String toString() {
        return "UnixTime{" + "time=" + new Date(getTime()).toString() + '}';
    }
}
