package com.innovation.arduino.state;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Description:
 * 小车的某一状态
 *
 * @author xxz
 * Created on 04/11/2018
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class State implements EncodeAble {
    @JsonUnwrapped
    private Infrared infrared;
    @JsonUnwrapped
    private Forward forward;
    @JsonProperty("action")
    private Motor motor;
    @JsonProperty("ultral")
    private Ultral ultral;
    @JsonUnwrapped
    private Voice voice;

    public Infrared getInfrared() {
        return infrared;
    }

    public void setInfrared(Infrared infrared) {
        this.infrared = infrared;
    }

    public Forward getForward() {
        return forward;
    }

    public void setForward(Forward forward) {
        this.forward = forward;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public Ultral getUltral() {
        return ultral;
    }

    public void setUltral(Ultral ultral) {
        this.ultral = ultral;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    /**
     * 将当前状态编码返回
     *
     * @return 长度为20字节的string
     */
    @Override
    public String encoding() {
        StringBuilder stringBuilder=new StringBuilder(20);
        stringBuilder.append(infrared.encoding());
        stringBuilder.append(forward.encoding());
        stringBuilder.append(motor.encoding());
        stringBuilder.append(motor.encoding());
        stringBuilder.append(voice.encoding());
        return new String(stringBuilder);
    }
}
