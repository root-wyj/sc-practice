package com.wyj.apps.common.core.query;

import lombok.Data;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/17
 */

@Data
public class BaseQuery {
    private int pageSize = 20;
    private int pageNum = 1;

    private String orderBy = "id";
    private String order = "desc";
}
