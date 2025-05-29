package com.oner365.swagger.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Polygon VO
 *
 * @author zhaoyong
 */
@ApiModel(value = "面")
public class PolygonVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "集合")
    private List<PointVo> points;

    public List<PointVo> getPoints() {
        return points;
    }

    public void setPoints(List<PointVo> points) {
        this.points = points;
    }

}
