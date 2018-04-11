package com.innovation.arduino.state;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Description:
 * 马达状态
 * @author xxz
 * Created on 04/11/2018
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Motor implements EncodeAble{

    /**
     * 是否启动马达
     */
    @JsonProperty("isActive")
    private boolean isActive;

    /**
     * 马达前进方向
     */
    @JsonProperty("direction")
    private Direction direction;

    /**
     * 速度，分为1，2，3，4 四个档位
     */
    @JsonProperty("speed")
    private int speed;

    /**
     * 持续时长，分为1，2，3，4 四个档位
     */
    @JsonProperty("time")
    private int time;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 将当前状态编码返回
     *
     * @return 长度为4字节的string
     */
    @Override
    public String encoding() {
        char c1,c2,c3,c4;
        c1=isActive?'C':'c';
        switch (direction){
            case FORWARD:c2='0';break;
            case LEFT:c2='1';break;
            case RIGHT:c2='2';break;
            default:c2='3';
        }
        c3=String.valueOf(speed%4).charAt(0);
        c4=String.valueOf(time%4).charAt(0);
        return ""+c1+c2+c3+c4;
    }
}
