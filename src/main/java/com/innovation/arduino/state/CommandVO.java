package com.innovation.arduino.state;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Description:
 * 前端发来的数据
 * @author xxz
 * Created on 04/11/2018
 */
public class CommandVO {
    /**
     * 主机状态
     */
    @JsonProperty("master")
    private List<State> master;

    /**
     * 从机状态
     */
    @JsonProperty("slave")
    private List<State> slave;

    public List<State> getMaster() {
        return master;
    }

    public void setMaster(List<State> master) {
        this.master = master;
    }

    public List<State> getSlave() {
        return slave;
    }

    public void setSlave(List<State> slave) {
        this.slave = slave;
    }
}
