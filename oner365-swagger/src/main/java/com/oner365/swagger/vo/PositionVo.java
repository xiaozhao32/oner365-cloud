package com.oner365.swagger.vo;

import java.io.Serializable;

import com.oner365.swagger.enums.PostgisTypeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Position VO
 *
 * @author zhaoyong
 */
@ApiModel(value = "位置信息")
public class PositionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 位置名称
     */
    @ApiModelProperty(value = "位置名称")
    private String positionName;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private PostgisTypeEnum postgisType;

    /**
     * 点
     */
    @ApiModelProperty(value = "点")
    private PointVo point;

    /**
     * 面
     */
    @ApiModelProperty(value = "面")
    private PolygonVo polygon;

    /**
     * 线
     */
    @ApiModelProperty(value = "线")
    private LineStringVo lineString;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public PointVo getPoint() {
        return point;
    }

    public void setPoint(PointVo point) {
        this.point = point;
    }

    public PolygonVo getPolygon() {
        return polygon;
    }

    public void setPolygon(PolygonVo polygon) {
        this.polygon = polygon;
    }

    public PostgisTypeEnum getPostgisType() {
        return postgisType;
    }

    public void setPostgisType(PostgisTypeEnum postgisType) {
        this.postgisType = postgisType;
    }

    public LineStringVo getLineString() {
        return lineString;
    }

    public void setLineString(LineStringVo lineString) {
        this.lineString = lineString;
    }

}
