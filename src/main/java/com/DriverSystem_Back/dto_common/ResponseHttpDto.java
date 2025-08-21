package com.DriverSystem_Back.dto_common;

public class ResponseHttpDto {
    private Integer codeHttp;
    private String messsage;
    private String role;
    private Boolean usaMfa;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ResponseHttpDto(Integer codeHttp, String messsage, String role, Boolean usaMfa, String userId) {
        this.codeHttp = codeHttp;
        this.messsage = messsage;
        this.role = role;
        this.usaMfa = usaMfa;
        this.userId = userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getUsaMfa() {
        return usaMfa;
    }

    public void setUsaMfa(Boolean usaMfa) {
        this.usaMfa = usaMfa;
    }
}
