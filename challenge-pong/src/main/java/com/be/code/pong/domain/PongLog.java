package com.be.code.pong.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName tb_pong_log
 */
@TableName(value ="tb_pong_log")
@Data
@Builder
public class PongLog implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 
     */
    @TableField(value = "receive_msg")
    private String receiveMsg;

    /**
     * 
     */
    @TableField(value = "result")
    private String result;

    /**
     * 
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "result_code")
    private Integer resultCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}