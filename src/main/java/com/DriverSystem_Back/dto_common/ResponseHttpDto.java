package com.DriverSystem_Back.dto_common;

public class ResponseHttpDto {

    private Integer codeHttp;

    private String messsage;

    public ResponseHttpDto(Integer codeHttp, String messsage) {
        this.codeHttp = codeHttp;
        this.messsage = messsage;
    }

    public Integer getCodeHttp() {
        return codeHttp;
    }

    public void setCodeHttp(Integer codeHttp) {
        this.codeHttp = codeHttp;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
