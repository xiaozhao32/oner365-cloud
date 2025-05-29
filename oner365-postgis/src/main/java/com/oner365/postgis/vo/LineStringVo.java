package com.oner365.postgis.vo;

import java.io.Serializable;
import java.util.List;

/**
 * LineString
 *
 * @author zhaoyong
 */
public class LineStringVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Point List
     */
    private List<PointVo> points;

    public LineStringVo() {
        super();
    }

    public List<PointVo> getPoints() {
        return points;
    }

    public void setPoints(List<PointVo> points) {
        this.points = points;
    }

}
