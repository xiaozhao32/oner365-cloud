package com.oner365.swagger.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 坐标
 *
 * @author zhaoyong
 *
 */
@ApiModel(value = "坐标信息")
public class GeoPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "纬度")
    private double lat;

    @ApiModelProperty(value = "经度")
    private double lon;

    public GeoPoint() {
        super();
    }

    public GeoPoint(double latitude, double longitude) {
        this.lat = latitude;
        this.lon = longitude;
    }

    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * @return the lon
     */
    public double getLon() {
        return lon;
    }

    /**
     * @param lon the lon to set
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

}
