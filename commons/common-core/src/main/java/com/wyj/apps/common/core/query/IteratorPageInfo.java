package com.wyj.apps.common.core.query;

import lombok.Data;

import java.util.List;

/**
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
@Data
public class IteratorPageInfo<T> extends IteratorPageQuery{

    private List<T> data;
    private boolean hasMore;
}
