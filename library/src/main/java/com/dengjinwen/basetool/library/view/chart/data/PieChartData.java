package com.dengjinwen.basetool.library.view.chart.data;

import com.dengjinwen.basetool.library.view.chart.interfaces.IPieChartData;

public class PieChartData implements IPieChartData {

    private float value;
    private int color;
    private String marker;

    public PieChartData(float value, int color,String marker) {
        this.value = value;
        this.color = color;
        this.marker=marker;
    }

    @Override
    public String getMarker() {
        return marker;
    }

    @Override
    public void setMarker(String marker) {
        this.marker = marker;
    }

    @Override
    public float getValue() {
        return value;
    }

    @Override
    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }
}
