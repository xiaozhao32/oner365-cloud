package com.oner365.postgis.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
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
import com.oner365.postgis.vo.LineStringVo;
import com.oner365.postgis.vo.PointVo;
import com.oner365.postgis.vo.PolygonVo;
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
    return StreamSupport.stream(list.spliterator(), false).map(this::builder).collect(Collectors.toList());
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
    setPoint(entity, vo);

    // linestring
    setLineString(entity, vo);

    // polygon
    setPolygon(entity, vo);

    // multiPoint
    setMultiPoint(entity, vo);

    // multiLineString
    setMultiLineString(entity, vo);

    // multiPolygon
    setMultiPolygon(entity, vo);

    Position po = repository.save(entity);
    return builder(po);
  }

  private GeometryFactory buildGeometryFactory() {
    return new GeometryFactory(new PrecisionModel(), PostgisConstants.SRID);
  }

  private void setPoint(Position entity, PositionVo vo) {
    if (vo.getPoint() != null) {
      Point point = buildGeometryFactory().createPoint(new Coordinate(vo.getPoint().getX(), vo.getPoint().getY()));
      point.setSRID(PostgisConstants.SRID);
      entity.setPostgisType(PostgisTypeEnum.POINT);
      entity.setPositionPoint(point);
    }
  }

  private void setMultiPoint(Position entity, PositionVo vo) {
    if (vo.getMultiPoint() != null) {
      MultiPoint multiPoint = buildGeometryFactory().createMultiPointFromCoords(setCoordinate(vo.getMultiPoint().getPoints()));
      multiPoint.setSRID(PostgisConstants.SRID);
      entity.setPostgisType(PostgisTypeEnum.MULTIPOINT);
      entity.setMultiPoint(multiPoint);
    }
  }

  private void setLineString(Position entity, PositionVo vo) {
    if (vo.getLineString() != null) {
      LineString lineString = buildGeometryFactory().createLineString(setCoordinate(vo.getLineString().getPoints()));
      lineString.setSRID(PostgisConstants.SRID);
      entity.setPostgisType(PostgisTypeEnum.LINESTRING);
      entity.setPositionLineString(lineString);
    }
  }

  private void setMultiLineString(Position entity, PositionVo vo) {
    if (vo.getMultiLineString() != null) {
      List<LineStringVo> lineStringVos = vo.getMultiLineString().getLineStrings();
      List<LineString> lineStrings = lineStringVos.stream().filter(lineStringVo -> !lineStringVo.getPoints().isEmpty())
          .map(lineStringVo -> buildGeometryFactory().createLineString(setCoordinate(lineStringVo.getPoints())))
          .collect(Collectors.toList());

      MultiLineString multiLineString = buildGeometryFactory()
          .createMultiLineString(lineStrings.toArray(new LineString[0]));
      multiLineString.setSRID(PostgisConstants.SRID);
      entity.setPostgisType(PostgisTypeEnum.MULTILINESTRING);
      entity.setMultiLineString(multiLineString);
    }
  }

  private void setPolygon(Position entity, PositionVo vo) {
    if (vo.getPolygon() != null) {
      Polygon polygon = buildGeometryFactory().createPolygon(setCoordinate(vo.getPolygon().getPoints()));
      polygon.setSRID(PostgisConstants.SRID);
      entity.setPostgisType(PostgisTypeEnum.POLYGON);
      entity.setPositionPolygon(polygon);
    }
  }

  private void setMultiPolygon(Position entity, PositionVo vo) {
    if (vo.getMultiPolygon() != null) {
      List<PolygonVo> polygonVos = vo.getMultiPolygon().getPolygons();
      List<Polygon> polygons = polygonVos.stream().filter(polygonVo -> !polygonVo.getPoints().isEmpty())
          .map(polygonVo -> buildGeometryFactory().createPolygon(setCoordinate(polygonVo.getPoints())))
          .collect(Collectors.toList());

      MultiPolygon multiPolygon = buildGeometryFactory().createMultiPolygon(polygons.toArray(new Polygon[0]));
      multiPolygon.setSRID(PostgisConstants.SRID);
      entity.setPostgisType(PostgisTypeEnum.MULTIPOLYGON);
      entity.setMultiPolygon(multiPolygon);
    }
  }

  private Coordinate[] setCoordinate(List<PointVo> points) {
    List<Coordinate> coordinates = points.stream().map(point -> new Coordinate(point.getX(), point.getY()))
        .collect(Collectors.toList());
    return coordinates.toArray(new Coordinate[0]);
  }

  @Override
  public PositionDto getById(String id) {
    Position po = repository.findById(id).orElse(null);
    return builder(po);
  }

  private PositionDto builder(Position entity) {
    try {
      if (entity != null) {
        PositionDto result = new PositionDto();
        result.setId(entity.getId());
        result.setPositionName(entity.getPositionName());
        result.setPostgisType(entity.getPostgisType());
        result.setCreateTime(entity.getCreateTime());
        result.setUpdateTime(entity.getUpdateTime());

        // point
        builderPoint(result, entity);

        // lineString
        builderLineString(result, entity);

        // polygon
        builderPolygon(result, entity);

        // multiPoint
        builderMultiPoint(result, entity);

        // multiLineString
        builderMultiLineString(result, entity);

        // multiPolygon
        builderMultiPolygon(result, entity);

        return result;
      }
    } catch (Exception e) {
      logger.error("Position entity error", e);
    }
    return null;
  }

  private void builderPoint(PositionDto result, Position entity) {
    if (entity.getPositionPoint() != null) {
      result.setPoint(
          new org.springframework.data.geo.Point(entity.getPositionPoint().getX(), entity.getPositionPoint().getY()));
    }
  }

  private void builderMultiPoint(PositionDto result, Position entity) {
    if (entity.getMultiPoint() != null) {
      result.setMultiPoint(builderPointList(entity.getMultiPoint().getCoordinates()));
    }
  }

  private void builderLineString(PositionDto result, Position entity) {
    if (entity.getPositionLineString() != null) {
      result.setLineString(new org.springframework.data.geo.Polygon(builderPointList(entity.getPositionLineString().getCoordinates())));
    }
  }

  private void builderMultiLineString(PositionDto result, Position entity) {
    if (entity.getMultiLineString() != null) {
      MultiLineString multiLineString = entity.getMultiLineString();
      List<org.springframework.data.geo.Polygon> multiLineStringList = IntStream.range(0, multiLineString.getNumGeometries())
              .mapToObj(i -> multiLineString.getGeometryN(i).getCoordinates())
              .map(coordinates -> new org.springframework.data.geo.Polygon(builderPointList(coordinates)))
              .collect(Collectors.toList());
      result.setMultiLineString(multiLineStringList);
    }
  }

  private void builderPolygon(PositionDto result, Position entity) {
    if (entity.getPositionPolygon() != null) {
      result.setPolygon(new org.springframework.data.geo.Polygon(builderPointList(entity.getPositionPolygon().getCoordinates())));
    }
  }

  private void builderMultiPolygon(PositionDto result, Position entity) {
    if (entity.getMultiPolygon() != null) {
      MultiPolygon multiPolygon = entity.getMultiPolygon();
      List<org.springframework.data.geo.Polygon> polygonList = IntStream.range(0, multiPolygon.getNumGeometries())
              .mapToObj(i -> multiPolygon.getGeometryN(i).getCoordinates())
              .map(coordinates -> new org.springframework.data.geo.Polygon(builderPointList(coordinates)))
              .collect(Collectors.toList());
        result.setMultiPolygon(polygonList);
    }
  }

  private List<org.springframework.data.geo.Point> builderPointList(Coordinate[] coordinates) {
    return Arrays.stream(coordinates)
            .map(coordinate -> new org.springframework.data.geo.Point(coordinate.getX(), coordinate.getY()))
            .collect(Collectors.toList());
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean deleteById(String id) {
    repository.deleteById(id);
    return Boolean.TRUE;
  }
}
