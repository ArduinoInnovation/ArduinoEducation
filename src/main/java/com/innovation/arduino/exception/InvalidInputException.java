package com.innovation.arduino.exception;

/**
 * Description:
 * 非法输入异常
 *
 * @author wym
 * Created on 03/21/2018
 * <p>
 * Update:
 * @author xxz
 * Last modified on
 */
public class InvalidInputException extends RuntimeException {
    /*
     * 情况1：传入的obj为null对象
     * 情况2：传入的obj的id为null*/
    public InvalidInputException(String message) {
        super(message);
    }
}
