package com.dengjinwen.basetool.library.view.chart.data;

public class PieChartData {

    private float value;
    private int color;
    private String marker;

    public PieChartData(float value, int color,String marker) {
        this.value = value;
        this.color = color;
        this.marker=marker;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
