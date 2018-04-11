package com.innovation.arduino.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Description:
 *
 * @author xxz
 * Created on 03/20/2018
 * <p>
 * Update:
 * @author xxz
 * Last modified on
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RestErrorInformation {
    @JsonProperty(value = "error")
    private String cause;//NOPMD

    public RestErrorInformation(String cause) {
        this.cause = cause;
    }
}
