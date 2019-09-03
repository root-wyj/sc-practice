package com.wyj.apps.common.core.query;

import lombok.Data;

import java.util.List;

/**
 * <pre>
 *         ("SELECT " + COLUMNS + " FROM " + TABLE_NAME +
 * //            " where 1=1" +
 *             "<if test=\"@org.apache.commons.lang3.StringUtils@isEmpty(query.title)==false\">where title like concat('%',#{query.title},'%')</if>" +
 *             " order by ${query.orderBy} ${query.order}" +
 *             " limit ${query.pageSize} offset ${offset}" +
 *             "")
 *         int count = questionMapper.count(query);
 *         pageInfo.setRecordNum(count);
 *         pageInfo.setPageNum(query.getPageNum());
 *         pageInfo.setPageSize(query.getPageSize());
 *         pageInfo.setPageCount((int)Math.ceil(count*1.0/query.getPageSize()));
 *         pageInfo.setData(questionMapper.pageList(query, (query.getPageNum()-1)*query.getPageSize()));
 * </pre>
 * Created
 * Author: wyj
 * Date: 2019/9/3
 */
@Data
public class PageInfo<T> {
    private List<T> data;

    private int pageNum;
    private int pageSize;
    private int pageCount;
    private int recordNum;
}
