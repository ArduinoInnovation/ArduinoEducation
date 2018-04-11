package com.innovation.arduino.state;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Description:
 * 是否转发控制字
 * @author xxz
 * Created on 04/11/2018
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Forward implements EncodeAble{
    /**
     * 是否转发控制字
     */
    @JsonProperty(value = "forward")
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * 将当前状态编码返回
     *
     * @return 长度为4字节的string
     */
    @Override
    public String encoding() {
        if(isActive){
            return "B123";
        }else {
            return "b123";
        }
    }
}
