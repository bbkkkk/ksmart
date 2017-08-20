<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.ksmart.pms.dao.UserDao">
    <insert id="insert" parameterType="Map">
        insert into smt_user
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="id != null">
                id,
            </if>
            <if test="uname != null">
                uname,
            </if>
            <if test="pwd != null">
                pwd,
            </if>
            <if test="real_name != null">
                real_name,
            </if>
            <if test="cert_no != null">
                cert_no,
            </if>
            <if test="birthday != null">
                birthday,
            </if>
            <if test="sex != null">
                sex,
            </if>
            <if test="comment != null">
                comment,
            </if>
            <if test="SE_UID != null">
                cid,
            </if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <if test="id != null">
            #{id},
            </if>
            <if test="uname != null">
            #{uname},
            </if>
            <if test="pwd != null">
            #{pwd},
            </if>
            <if test="real_name != null">
            #{real_name},
            </if>
            <if test="cert_no != null">
            #{cert_no},
            </if>
            <if test="birthday != null">
            #{birthday},
            </if>
            <if test="sex != null">
            #{sex},
            </if>
            <if test="comment != null">
            #{comment},
            </if>
            <if test="SE_UID != null">
            #{SE_UID},
            </if>
        </trim>
    </insert>
    <update id="update">
        update
        smt_user
        <trim prefix="set" suffixOverrides=",">
            <if test="uname!=null">uname=#{uname},</if>
            <if test="pwd!=null">pwd=#{pwd},</if>
            <if test="real_name!=null">real_name=#{real_name},</if>
            <if test="cert_no!=null">cert_no=#{cert_no},</if>
            <if test="birthday!=null">birthday=#{birthday},</if>
            <if test="sex!=null">sex=#{sex},</if>
            <if test="comment!=null">comment=#{comment},</if>
            <if test="SE_UID!=null">uid=#{SE_UID},</if>
        </trim>
        where id = #{id} and isdel=0
    </update>
    <update id="lgDelById">
        update smt_user set isdel=1,uid=#{SE_UID} where id = #{id}
    </update>
    <select id="queryListById" resultType="Map">
        select * from smt_user where  id=#{id} and isdel=0
    </select>
    <delete id="delById">
        delete from smt_user where id=#{id}
    </delete>
    <select id="queryCount" parameterType="Map" resultType="Integer">
        select count(1) from smt_user t
        <where>
            isdel=0
            <if test="params!=null and params.size()!=0">
                and
                <foreach collection="params" index="key" item="value" separator=" and ">
                ${key} like CONCAT('%',#{value},'%' )
                </foreach>
            </if>
        </where>

    </select>
    <select id="queryPage" resultType="Map">
        select id,uname,pwd,real_name,cert_no,sex,ctime, utime from smt_user
        <where>
            isdel=0
            <if test="params!=null and params.size()!=0">
                and
                <foreach collection="params" index="key" item="value" separator=" and ">
                ${key} like CONCAT('%',#{value},'%' )
                </foreach>
            </if>
        </where>
        order by ${sortname} ${sortorder} limit #{limit_start},#{limit_end}
    </select>
</mapper>