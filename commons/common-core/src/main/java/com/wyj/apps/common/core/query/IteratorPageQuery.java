package com.wyj.apps.common.core.query;

import lombok.Data;

/*
    @Select("<script>select " + ALL_COLUMNS + " from " +TABLE_NAME +
            " where 1=1" +
            " <if test=\"lastRecordId!=null\">and id &lt; #{lastRecordId} </if>" +
            " order by id desc" +
            " limit ${pageSize}" +
            "</script>")
    List<MerchantProductRecord> pageQueryRecord(@Param("alipayUserId") String alipayUserId,
                                                @Param("lastRecordId") Long recordId,
                                                @Param("pageSize") int pageSize);
 */
/**
 * 迭代分页查询。根据id 或者时间戳 排好序筛选
 * Created
 * Author: wyj
 * Date: 2019/11/28
 */
@Data
public class IteratorPageQuery{

    private Long id;
    private Long timestamp;
    private Integer pageSize;
}
