package com.oner365.postgis.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.postgis.constants.PostgisConstants;
import com.oner365.postgis.dto.PositionDto;
import com.oner365.postgis.entity.Position;
import com.oner365.postgis.enums.PostgisTypeEnum;
import com.oner365.postgis.repository.IPositionRepository;
import com.oner365.postgis.service.IPositionService;
import com.oner365.postgis.vo.PointVo;
import com.oner365.postgis.vo.PositionVo;

/**
 * Position Service Impl
 *
 * @author zhaoyong
 */
@Service
public class PositionServiceImpl implements IPositionService {

  private final Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

  @Resource
  private IPositionRepository repository;

  @Override
  public List<PositionDto> findList() {
    Iterable<Position> list = repository.findAll();
    List<PositionDto> result = new ArrayList<>();
    for (Position po : list) {
      result.add(builder(po));
    }
    return result;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public PositionDto save(PositionVo vo) {
    Position entity = new Position();
    entity.setId(vo.getId());
    entity.setPositionName(vo.getPositionName());
    if (entity.getId() == null) {
      entity.setCreateTime(LocalDateTime.now());
    }
    entity.setUpdateTime(LocalDateTime.now());

    // point
    if (vo.getPoint() != null) {
      GeometryFactory geometryFactory = new GeometryFactory();
      Point point = geometryFactory.createPoint(new Coordinate(vo.getPoint().getX(), vo.getPoint().getY()));
      point.setSRID(PostgisConstants.SRID);
      entity.setPostgisType(PostgisTypeEnum.POINT);
      entity.setPositionPoint(point);
    }
    // polygon
    if (vo.getPolygon() != null) {
      List<PointVo> points = vo.getPolygon().getPoints();
      List<Coordinate> coordinates = new ArrayList<>();
      for (PointVo point : points) {
        coordinates.add(new Coordinate(point.getX(), point.getY()));
      }

      GeometryFactory geometryFactory = new GeometryFactory();
      Polygon polygon = geometryFactory.createPolygon(coordinates.toArray(new Coordinate[0]));
      polygon.setSRID(PostgisConstants.SRID);
      entity.setPostgisType(PostgisTypeEnum.POLYGON);
      entity.setPositionPolygon(polygon);
    }

    Position po = repository.save(entity);
    return builder(po);
  }

  @Override
  public PositionDto getById(String id) {
    Position po = repository.findById(id).orElse(null);
    return builder(po);
  }

  private PositionDto builder(Position entity) {
    try {
      PositionDto result = new PositionDto();
      // point
      if (entity.getPositionPoint() != null) {
        org.springframework.data.geo.Point point = new org.springframework.data.geo.Point(entity.getPositionPoint().getX(),
            entity.getPositionPoint().getY());
        result.setPoint(point);
      }
      // polygon
      if (entity.getPositionPolygon() != null) {
        List<org.springframework.data.geo.Point> pointList = new ArrayList<>();
        for (Coordinate coordinate : entity.getPositionPolygon().getCoordinates()) {
          org.springframework.data.geo.Point point = new org.springframework.data.geo.Point(coordinate.getX(),
              coordinate.getY());
          pointList.add(point);
        }
        org.springframework.data.geo.Polygon polygon = new org.springframework.data.geo.Polygon(pointList);
        result.setPolygon(polygon);
      }

      result.setId(entity.getId());
      result.setPositionName(entity.getPositionName());
      result.setPostgisType(entity.getPostgisType());
      result.setCreateTime(entity.getCreateTime());
      result.setUpdateTime(entity.getUpdateTime());

      return result;
    } catch (Exception e) {
      logger.error("Position entity error", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean deleteById(String id) {
    repository.deleteById(id);
    return Boolean.TRUE;
  }
}
