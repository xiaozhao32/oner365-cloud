package com.oner365.elasticsearch.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;
import com.oner365.elasticsearch.enums.GeneTypeEnum;

/**
 * SampleGene
 *
 * @author zhaoyong
 */
public class SampleGeneDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 基因类型 (格式: 1:X 2:Y)
     */
    private GeneTypeEnum geneType;

    /**
     * 人员编号
     */
    private String personCode;

    /**
     * 实验室编号
     */
    private String initServerNo;

    /**
     * 基因型信息 (格式: {key:value} )
     */
    private JSONObject geneInfo;

    /**
     * 比对的基因型信息 (过滤空值)
     */
    private JSONObject matchJson;

    /**
     * 页面使用的基因型 {key:value} 转换成 {"name":key, "value":value} 格式
     */
    private JSONArray geneList;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the geneType
     */
    public GeneTypeEnum getGeneType() {
        return geneType;
    }

    /**
     * @param geneType the geneType to set
     */
    public void setGeneType(GeneTypeEnum geneType) {
        this.geneType = geneType;
    }

    /**
     * @return the personCode
     */
    public String getPersonCode() {
        return personCode;
    }

    /**
     * @param personCode the personCode to set
     */
    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    /**
     * @return the initServerNo
     */
    public String getInitServerNo() {
        return initServerNo;
    }

    /**
     * @param initServerNo the initServerNo to set
     */
    public void setInitServerNo(String initServerNo) {
        this.initServerNo = initServerNo;
    }

    /**
     * @return the geneInfo
     */
    public JSONObject getGeneInfo() {
        return geneInfo;
    }

    /**
     * @param geneInfo the geneInfo to set
     */
    public void setGeneInfo(JSONObject geneInfo) {
        this.geneInfo = geneInfo;
    }

    /**
     * @return the matchJson
     */
    public JSONObject getMatchJson() {
        return matchJson;
    }

    /**
     * @param matchJson the matchJson to set
     */
    public void setMatchJson(JSONObject matchJson) {
        this.matchJson = matchJson;
    }

    /**
     * @return the geneList
     */
    public JSONArray getGeneList() {
        return geneList;
    }

    /**
     * @param geneList the geneList to set
     */
    public void setGeneList(JSONArray geneList) {
        this.geneList = geneList;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}
