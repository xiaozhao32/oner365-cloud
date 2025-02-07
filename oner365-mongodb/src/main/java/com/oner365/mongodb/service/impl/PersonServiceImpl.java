package com.oner365.mongodb.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import com.oner365.data.commons.exception.ProjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.mongodb.dto.PersonDto;
import com.oner365.mongodb.entity.Person;
import com.oner365.mongodb.repository.PersonRepository;
import com.oner365.mongodb.service.IPersonService;
import com.oner365.mongodb.vo.PersonVo;

/**
 * Person Service impl
 *
 * @author zhaoyong
 */
@Service
public class PersonServiceImpl implements IPersonService {

  private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

  @Resource
  private PersonRepository repository;

  @Override
  @Transactional(rollbackFor = ProjectException.class)
  public PersonDto save(PersonVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setCreateTime(DateUtil.dateToTimestamp(DateUtil.getDate()));
    }
    Person entity = repository.save(convertVo(vo));
    return convertDto(entity);
  }

  @Override
  public PersonDto getById(String id) {
    Optional<Person> optional = repository.findById(id);
    return convertDto(optional.orElse(null));
  }

  @Override
  public PageInfo<PersonDto> page(QueryCriteriaBean data) {
    try {
      Page<Person> page = repository.findAll(QueryUtils.buildPageRequest(data));
      List<Person> personList = page.getContent();
      List<PersonDto> personDtoList = personList.stream().map(this::convertDto)
          .collect(Collectors.toList());
      return new PageInfo<>(personDtoList, page.getNumber() + 1, page.getSize(), page.getTotalElements());
    } catch (Exception e) {
      logger.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectException.class)
  public Boolean delete(String id) {
    try {
      repository.deleteById(id);
      return Boolean.TRUE;
    } catch (Exception e) {
      logger.error("Error delete: ", e);
    }
    return Boolean.FALSE;
  }

  private Person convertVo(@NotNull PersonVo vo) {
    Person result = new Person();
    result.setAge(vo.getAge());
    result.setCreateTime(vo.getCreateTime().toInstant());
    result.setId(vo.getId());
    result.setName(vo.getName());
    result.setUpdateTime(Instant.now());
    return result;
  }

  private PersonDto convertDto(Person entity) {
    if (entity == null) {
      return null;
    }
    PersonDto result = new PersonDto();
    result.setAge(entity.getAge());
    result.setCreateTime(Timestamp.from(entity.getCreateTime()));
    result.setId(entity.getId());
    result.setName(entity.getName());
    result.setUpdateTime(Timestamp.from(entity.getUpdateTime()));
    return result;
  }
}
