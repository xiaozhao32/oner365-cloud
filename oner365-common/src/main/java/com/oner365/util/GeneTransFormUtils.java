package com.oner365.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.constants.PublicConstants;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 基因型工具类
 *
 * @author zhaoyong
 */
public class GeneTransFormUtils {

    /**
     * 构造方法
     */
    private GeneTransFormUtils() {
    }

    /**
     * 转换基因格式
     * 转换格式 {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539": "10/13"}
     * 目标类型 [{"name": "D8S1179", "value": "11,12"}, {"name": "D2S11", "value": "9,10"} ......]
     */
    public static JSONArray geneFormatList(String geneInfo) {
        JSONArray result = new JSONArray();
        if (!DataUtils.isEmpty(geneInfo)) {
            JSONObject jsonObject = JSON.parseObject(geneInfo);
            jsonObject.forEach((key, value) -> {
                JSONObject json = new JSONObject();
                json.put("name", key);
                json.put("value", value);
                result.add(json);
            });
        }
        return result;
    }

    /**
     * 转换基因格式
     * 目标类型 [{"name": "D8S1179", "value": "11,12"}, {"name": "D2S11", "value": "9,10"} ......]
     * 转换格式 {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539": "10/13"}
     */
    public static JSONObject geneFormatString(String geneInfo) {
        JSONObject result = new JSONObject();
        if (!DataUtils.isEmpty(geneInfo)) {
            JSONArray jsonArray = JSON.parseArray(geneInfo);
            jsonArray.forEach(array -> {
                JSONObject json = (JSONObject) array;
                result.put(json.get("name").toString(), json.get("value"));
            });
        }
        return result;
    }

    /**
     * 过滤空基因 (字符串冒号后面不能有空格)
     * 转换格式 {"D7S820":"", "D12S391":"18/18", "D13S317":"11/12", "D16S539":"10/13"}
     * 目标类型 {"D12S391":"18/18", "D13S317":"11/12", "D16S539":"10/13"}
     */
    public static String geneTrimString(String geneInfo) {
        return geneInfo.replaceAll("((?<=\\{)\"\\w+\":\"\",|,*\"\\w+\":\"\")", "");
    }

    /**
     * 判断2个json是否包含 后者是否包含前者，包含返回true 否则返回false
     *
     * @param matchJson 被比对单一基因型
     * @param geneJson  混合基因型
     * @return boolean
     */
    public static boolean match(JSONObject matchJson, JSONObject geneJson) {
        boolean flag = true;
        for (Map.Entry<String, Object> entry : matchJson.entrySet()) {
            String key = entry.getKey();
            if (geneJson.getString(key) != null) {
                List<String> value = new ArrayList<>(Arrays.asList(matchJson.getString(key).split(PublicConstants.DELIMITER)));
                List<String> gene = new ArrayList<>(Arrays.asList(geneJson.getString(key).split(PublicConstants.DELIMITER)));
                if (value.size() != value.stream().filter(gene::contains).count()) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 判断2个json是否包含 后者是否包含前者，包含返回true 否则返回false
     *
     * @param matchJson 被比中的单一基因型
     * @param geneJson  单一基因型
     * @return boolean
     */
    public static boolean matchEquals(JSONObject matchJson, JSONObject geneJson) {
        boolean flag = true;
        for (Map.Entry<String, Object> entry : geneJson.entrySet()) {
            String key = entry.getKey();
            if (geneJson.getString(key) != null && matchJson.getString(key) != null
                    && !geneJson.get(key).toString().equals(matchJson.getString(key))) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 同一比对测试 使用 matchEquals 测试用本方法
     *
     * @param matchJson 比对基因
     * @param geneJson  目标基因
     * @return Map
     */
    public static Map<String, Integer> matchGeneEquals(JSONObject matchJson, JSONObject geneJson) {
        int diff = 0;
        int trim = 0;
        int m = 0;
        for (Map.Entry<String, Object> entry : geneJson.entrySet()) {
            String key = entry.getKey();
            if (geneJson.getString(key) != null && matchJson.getString(key) != null) {
                if (!geneJson.get(key).toString().equals(matchJson.getString(key))) {
                    diff++;
                } else {
                    m++;
                }
            } else {
                trim++;
            }
        }

        Map<String, Integer> result = Maps.newHashMap();
        result.put("totalCount", matchJson.keySet().size());
        result.put("matchCount", m);
        result.put("diffCount", diff);
        result.put("trimCount", trim);
        return result;
    }

    /**
     * 混合比对测试 使用 match 测试用本方法
     *
     * @param matchJson 比对基因
     * @param geneJson  目标基因
     * @return Map
     */
    public static Map<String, Integer> matchGeneContains(JSONObject matchJson, JSONObject geneJson) {
        int diff = 0;
        int trim = 0;
        int m = 0;
        for (String key : matchJson.keySet()) {
            if (geneJson.getString(key) != null) {
                Set<String> value = new HashSet<>(Arrays.asList(matchJson.getString(key).split(PublicConstants.DELIMITER)));
                Set<String> gene = new HashSet<>(Arrays.asList(geneJson.get(key).toString().split(PublicConstants.DELIMITER)));
                Set<String> s = Sets.difference(value, gene);
                if (s.isEmpty()) {
                    m++;
                }
            } else {
                if (DataUtils.isEmpty(matchJson.getString(key))) {
                    trim++;
                } else {
                    diff++;
                }
            }
        }
        Map<String, Integer> result = Maps.newHashMap();
        result.put("totalCount", matchJson.keySet().size());
        result.put("matchCount", m);
        result.put("diffCount", diff);
        result.put("trimCount", trim);
        return result;
    }

}
