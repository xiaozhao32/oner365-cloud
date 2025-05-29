package com.oner365.swagger.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

import com.oner365.swagger.enums.PostgisTypeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Position DTO
 *
 * @author zhaoyong
 */
@ApiModel(value = "位置信息")
public class PositionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "位置名称")
    private String positionName;

    @ApiModelProperty(value = "类型: 点:POINT, 面:POLYGON")
    private PostgisTypeEnum postgisType;

    @ApiModelProperty(value = "点")
    private Point point;

    @ApiModelProperty(value = "面")
    private Polygon polygon;

    @ApiModelProperty(value = "线")
    private Polygon lineString;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "修改时间")
    private Timestamp updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public PostgisTypeEnum getPostgisType() {
        return postgisType;
    }

    public void setPostgisType(PostgisTypeEnum postgisType) {
        this.postgisType = postgisType;
    }

    public Polygon getLineString() {
        return lineString;
    }

    public void setLineString(Polygon lineString) {
        this.lineString = lineString;
    }

}
