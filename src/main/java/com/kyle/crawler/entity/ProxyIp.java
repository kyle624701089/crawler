package com.kyle.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 代理服务器信息表
 * </p>
 *
 * @author kyle
 * @since 2019-09-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProxyIp extends Model<ProxyIp> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 国家
     */
    private String country;

    /**
     *  ip
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 服务器地址
     */
    private String serverAddr;

    /**
     * 是否匿名
     */
    private String noName;

    /**
     * http/https
     */
    private String serverType;

    /**
     * 存活时间
     */
    private String surviveTime;

    /**
     * 验证时间
     */
    private String validateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
