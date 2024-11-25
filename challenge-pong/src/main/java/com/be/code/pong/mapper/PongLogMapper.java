package com.be.code.pong.mapper;

import com.be.code.pong.domain.PongLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【tb_pong_log】的数据库操作Mapper
* @createDate 2024-11-23 21:28:22
* @Entity com.be.code.pong.domain.PongLog
*/
@Mapper
public interface PongLogMapper extends BaseMapper<PongLog> {

}




