package com.oner365.postgis.vo;

import java.io.Serializable;
import java.util.List;

/**
 * MultiLineString
 *
 * @author zhaoyong
 */
public class MultiLineStringVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * LineString List
     */
    private List<LineStringVo> lineStrings;

    public MultiLineStringVo() {
        super();
    }

    public List<LineStringVo> getLineStrings() {
        return lineStrings;
    }

    public void setLineStrings(List<LineStringVo> lineStrings) {
        this.lineStrings = lineStrings;
    }

}
