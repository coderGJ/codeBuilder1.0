<#-----author : GuoJun------->
<#-----date : 2018-03-03----->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${baseConfig.basePackage}.${baseConfig.beanPackage}.${(entity.className)!}">

    <resultMap id="BaseResultMap" type="${(entity.className)!}">
        <#if entity.primaryColumn??>
        <id column="${entity.primaryColumn.columnName}" jdbcType="${entity.primaryColumn.jdbcType}" property="${entity.primaryColumn.camelCaseName}" />
        </#if>
        <#if entity.columnList?? && (entity.columnList?size > 0) >
        <#list entity.columnList as column>
        <result column="${column.columnName}" jdbcType="${column.jdbcType}" property="${column.camelCaseName}" />
        </#list>
        </#if>
    </resultMap>

    <sql id="Base_Column_List">
    <#if entity.primaryColumn??>
        `${entity.primaryColumn.columnName}`,<#if entity.columnList?? && (entity.columnList?size > 0) ><#list entity.columnList as column> `${column.columnName}`<#if column_has_next>,</#if></#list></#if>
    </#if>
    </sql>

    <sql id="where_sql">
    <#if entity.primaryColumn??>
        <if test="${entity.primaryColumn.camelCaseName}">
            AND `${entity.primaryColumn.columnName}` = <#noparse>#{</#noparse>${entity.primaryColumn.camelCaseName}<#noparse>}</#noparse>
        </if>
    </#if>
    <#if entity.columnList?? && (entity.columnList?size > 0) >
        <#list entity.columnList as column>
        <#if column.columnType=="VARCHAR" || column.columnType=="CHAR" >
        <if test="${column.camelCaseName} != null and ${column.camelCaseName} != ''">
            AND `${column.columnName}` LIKE CONCAT('%', <#noparse>#{</#noparse>${column.camelCaseName}<#noparse>}</#noparse>, '%')
            <#else>
        <if test="${column.camelCaseName} != null">
            AND `${column.columnName}` = <#noparse>#{</#noparse>${column.camelCaseName}, jdbcType = ${column.jdbcType}<#noparse>}</#noparse>
        </#if>
        </if>
        </#list>
    </#if>
    </sql>

    <select id="selectList" parameterType="parameter" resultType="${(entity.className)!}">
        SELECT <include refid="Base_Column_List"/>
        FROM `${entity.tableName}`
        <where>
            <include refid="where_sql"/>
        </where>
        <if test="order != null">
            ORDER BY <#noparse>${order}</#noparse>
        </if>
    </select>

    <select id="selectList_count" parameterType="parameter" resultType="java.lang.Long">
        SELECT COUNT(1) FROM `${entity.tableName}`
        <where><include refid="where_sql"/></where>
    </select>

    <insert id="insert" parameterType="${entity.className}">
        <#if entity.primaryColumn??>
        <selectKey keyProperty="${entity.primaryColumn.columnName}" order="AFTER" resultType="java.lang.Long">
            SELECT LAST_INSERT_ID()
        </selectKey>
        </#if>
        INSERT INTO `${entity.tableName}` (<#if entity.columnList?? && (entity.columnList?size > 0) ><#list entity.columnList as column> `${column.columnName}`<#if column_has_next>,</#if></#list></#if>)
        VALUES (<#if entity.columnList?? && (entity.columnList?size > 0) ><#list entity.columnList as column><#noparse> #{</#noparse>${column.camelCaseName}, jdbcType = ${column.jdbcType}<#noparse>}</#noparse><#if column_has_next>,</#if></#list></#if>)
    </insert>

    <update id="update" parameterType="${entity.className}">
        UPDATE `${entity.tableName}`
        <set>
            <#if entity.columnList?? && (entity.columnList?size > 0) >
            <#list entity.columnList as column>
            <if test="${column.camelCaseName} != null">
                `${column.columnName}` = <#noparse>#{</#noparse>${column.camelCaseName}<#noparse>}</#noparse>,
            </if>
            </#list>
            </#if>
        </set>
        WHERE <#if entity.primaryColumn??>`${entity.primaryColumn.columnName}` = <#noparse>#{</#noparse>${entity.primaryColumn.camelCaseName}, jdbcType = ${entity.primaryColumn.jdbcType}<#noparse>}</#noparse></#if>
    </update>

    <delete id="delete" parameterType="<#if entity.primaryColumn.columnType == "BIGINT" || entity.primaryColumn.columnType == "INT">java.lang.Long<#else>java.lang.String</#if>">
        DELETE FROM `${entity.tableName}`
        WHERE <#if entity.primaryColumn??>`${entity.primaryColumn.columnName}` = <#noparse>#{</#noparse>${entity.primaryColumn.camelCaseName}, jdbcType = ${entity.primaryColumn.jdbcType}<#noparse>}</#noparse></#if>
    </delete>
</mapper>