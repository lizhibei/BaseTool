package com.dengjinwen.basetool.library.view.chart.interfaces;

public interface IPieChartData extends IChartData {

    /**
     * 设置单个扇形区域的数据
     * @param value
     */
    void setValue(float value);

    /**
     * 获得单个扇形区域的数据
     * @return
     */
    float getValue();

    /**
     * 设置扇形区域颜色
     * @param color
     */
    void setColor(int color);

    /**
     * 扇形区域颜色
     * @return
     */
    int getColor();

    /**
     * 设置扇形区域标记文本
     * @param marker
     */
    void setMarker(String marker);

    /**
     * 扇形区域标记文本
     * @return
     */
    String getMarker();
}
