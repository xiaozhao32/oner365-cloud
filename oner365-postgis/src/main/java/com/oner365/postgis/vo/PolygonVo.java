package com.oner365.postgis.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Polygon VO
 *
 * @author zhaoyong
 */
public class PolygonVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Point List
     */
    private List<PointVo> points;

    public PolygonVo() {
        super();
    }

    public List<PointVo> getPoints() {
        return points;
    }

    public void setPoints(List<PointVo> points) {
        this.points = points;
    }

}
