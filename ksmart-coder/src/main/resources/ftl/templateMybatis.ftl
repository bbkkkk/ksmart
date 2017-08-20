<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${ packageName }.dao.${classPre}Dao">
    <insert id="insert" parameterType="Map">
        insert into ${ tableName }
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list columns as col>
            <#if col.isEdit == 1>
                <if test="${col.fieldName}  != null">
                ${col.fieldName},
                </if>
            <#else>
            </#if>
        </#list>
            <if test="SE_UID != null">
                cid,
            </if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
        <#list columns as col>
            <#if col.isEdit == 1>
                <if test="${col.fieldName}  != null">
                ${r'#{'}${col.fieldName}${r'}'},
                </if>
            <#else>
            </#if>
        </#list>
            <if test="SE_UID != null">
            ${r'#{SE_UID}'},
            </if>
        </trim>
    </insert>
    <update id="update">
        update
    ${ tableName }
        <trim prefix="set" suffixOverrides=",">
        <#list columns as col>
            <#if col.isEdit == 1>
                <if test="${col.fieldName}  != null">
                ${col.fieldName}=${r'#{'}${col.fieldName}${r'}'},
                </if>
            <#else>
            </#if>
        </#list>
            <if test="SE_UID!=null">uid=${r'#{SE_UID}'},</if>
        </trim>
        where id = ${r'#{id}'} and isdel=0
    </update>
    <update id="lgDelById">
        update ${ tableName } set isdel=1,uid=${r'#{SE_UID}'} where id = ${r'#{id}'}
    </update>
    <select id="queryListById" resultType="Map">
        select * from ${ tableName } where id=${r'#{id}'} and isdel=0
    </select>
    <delete id="delById">
        delete from ${ tableName } where id=${r'#{id}'}
    </delete>
    <select id="queryCount" parameterType="Map" resultType="Integer">
        select count(1) from ${ tableName } t
        <where>
            isdel=0
            <if test="params!=null and params.size()!=0">
                and
                <foreach collection="params" index="key" item="value" separator=" and ">
                ${r'${key}'} like CONCAT('%',${r'#{value}'},'%' )
                </foreach>
            </if>
        </where>

    </select>
    <select id="queryPage" resultType="Map">
        <trim prefix="select " suffixOverrides=",">
    <#list columns as col>
        <#if col.showList==1>
        ${col.fieldName},
        <#else>
        </#if>
    </#list>
        </trim>
        from ${ tableName }
        <where>
            isdel=0
            <if test="params!=null and params.size()!=0">
                and
                <foreach collection="params" index="key" item="value" separator=" and ">
                ${r'${key}'} like CONCAT('%',${r'#{value}'},'%' )
                </foreach>
            </if>
        </where>
        order by ${r'${sortname}'} ${r'${sortorder}'} limit ${r'#{limit_start}'},${r'#{limit_end}'}
    </select>
</mapper>