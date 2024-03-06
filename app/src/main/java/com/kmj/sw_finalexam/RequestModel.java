package com.kmj.sw_finalexam;

public class RequestModel {
    private String storeName;
    private String requestText;
    private Integer goodDayBoxes;

    public RequestModel() {
        // Default constructor required for calls to DataSnapshot.getValue(RequestModel.class)
    }

    public RequestModel(String storeName, String requestText, Integer goodDayBoxes) {
        this.storeName = storeName;
        this.requestText = requestText;
        this.goodDayBoxes = goodDayBoxes;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getRequestText() {
        return requestText;
    }

    public Integer getGoodDayBoxes() {
        return goodDayBoxes;
    }

    // setter 추가
    public void setGoodDayBoxes(Integer goodDayBoxes) {
        this.goodDayBoxes = goodDayBoxes;
    }
}