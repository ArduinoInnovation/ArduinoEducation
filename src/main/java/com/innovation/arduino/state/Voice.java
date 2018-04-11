package com.innovation.arduino.state;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Description:
 * 语音识别模块
 * @author xxz
 * Created on 04/11/2018
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Voice implements EncodeAble{
    @JsonProperty("voice")
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
            return "E123";
        }else{
            return "e123";
        }
    }
}
