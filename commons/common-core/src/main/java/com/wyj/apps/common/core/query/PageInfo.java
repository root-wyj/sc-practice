package com.wyj.apps.common.core.query;

import lombok.Data;

import java.util.List;

/*
    @Select("<script> SELECT " + ALL_COLUMNS + " from " +TABLE_NAME +
            " where 1=1" +
            " <if test=\"vo.name!=null\">and productName like CONCAT('%',#{vo.name},'%') </if>" +
            " order by ${vo.orderBy} ${vo.desc}" +
            " LIMIT ${vo.pageSize} OFFSET ${(vo.pageNum-1)*vo.pageSize}" +
            "</script>")
    List<MerchantProduct> pageQuery(@Param("vo")MerchantProductPageVo vo);

    @Select("<script> SELECT count(*) from " + TABLE_NAME +
            " where 1=1" +
            " <if test=\"vo.name!=null\">and productName like CONCAT('%',#{vo.name},'%') </if>" +
            " order by ${vo.orderBy} ${vo.desc}" +
            " LIMIT ${vo.pageSize} OFFSET ${(vo.pageNum-1)*vo.pageSize}" +
            "</script>")
    int countPageQuery(@Param("vo")MerchantProductPageVo vo);

    @Select({"<script>" +
            "SELECT * FROM `card_right_flow` " +
            " WHERE `alipayUserId` = #{alipayUserId} " +
            " AND `status` = 1" +
            " AND rightDrawWay in " +
            "      <foreach collection=\"drawTypeList\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n" +
            "        #{item}\n" +
            "      </foreach>" +
            " ORDER BY `createTime` DESC " +
            " LIMIT ${pageSize} OFFSET ${(startPage-1)*pageSize}" +
            "</script>"})
    List<CardRightFlow> pageingFreeRightList(@Param("alipayUserId") String alipayUserId, @Param("drawTypeList") List<String> drawTypeList,
                                             @Param("startPage") int startPage,@Param("pageSize") int pageSize);

 */
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
 *
 * <br/>
 *
 *
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
