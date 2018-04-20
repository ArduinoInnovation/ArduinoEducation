package com.innovation.arduino.state;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Description:
 * 超声波避障
 * @author xxz
 * Created on 04/11/2018
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Ultral implements EncodeAble{
    /**
     * 是否生效
     */
    @JsonProperty("isActive")
    private boolean isActive;

    /**
     * 半径
     */
    @JsonProperty("r")
    private int r;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    /**
     * 将当前状态编码返回
     *
     * @return 长度为4字节的string
     */
    @Override
    public String encoding() {
        char c1,c2;
        c1=isActive?'D':'d';
        c2=String.valueOf(r%4).charAt(0);
        return ""+c1+c2+"34";
    }
}
