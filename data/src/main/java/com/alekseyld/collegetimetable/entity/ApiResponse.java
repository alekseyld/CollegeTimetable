package com.alekseyld.collegetimetable.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alekseyld on 02.09.2017.
 */

public class ApiResponse {

    @SerializedName("status")
    int status;

    @SerializedName("result")
    String result;

    @SerializedName("error")
    String error;


    public int getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
