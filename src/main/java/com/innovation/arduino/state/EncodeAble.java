package com.innovation.arduino.state;

/**
 * Description:
 * 所有可以编码的状态
 * @author xxz
 * Created on 04/11/2018
 */
public interface EncodeAble {
    /**
     * 将当前状态编码返回
     * @return 长度为4字节的string
     */
     String encoding();
}
