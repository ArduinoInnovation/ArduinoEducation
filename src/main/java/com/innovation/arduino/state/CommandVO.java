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
    List<State> master;

    /**
     * 从机状态
     */
    @JsonProperty("slave")
    List<State> slave;
}
