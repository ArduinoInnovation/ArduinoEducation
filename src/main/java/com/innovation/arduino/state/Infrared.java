package com.innovation.arduino.state;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Description:
 * 红外线模块
 * @author xxz
 * Created on 04/11/2018
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Infrared implements EncodeAble{
    /**
     * 是否开启红外线
     */
    @JsonProperty(value = "infrared")
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
            return "A123";
        }else{
            return "a123";
        }
    }
}
