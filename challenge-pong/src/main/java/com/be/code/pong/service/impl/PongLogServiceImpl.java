package com.be.code.pong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.be.code.pong.domain.PongLog;
import com.be.code.pong.service.PongLogService;
import com.be.code.pong.mapper.PongLogMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【tb_pong_log】的数据库操作Service实现
* @createDate 2024-11-23 21:28:22
*/
@Service
public class PongLogServiceImpl extends ServiceImpl<PongLogMapper, PongLog>
    implements PongLogService{

}




