package com.kmj.sw_finalexam;

public class ResultData {
    private String selectedDate;
    private String resultText;

    // 기본 생성자 (Firebase에서 데이터를 읽어올 때 필요)
    public ResultData() {
    }

    public ResultData(String selectedDate, String resultText) {
        this.selectedDate = selectedDate;
        this.resultText = resultText;
    }

    // Getter 메소드 (Firebase에서 데이터를 읽어올 때 필요)
    public String getSelectedDate() {
        return selectedDate;
    }

    public String getResultText() {
        return resultText;
    }
}
