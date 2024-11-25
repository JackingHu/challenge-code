package com.be.code.ping.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *
 * @Author: JK
 * @Date: 2024/11/22 10:54
 */
@Slf4j
@Component
public class FileLockUtils {

    /**
     * File Lock的文件, 目前内容是2行数据
     */
    @Value("fileLock.file")
    private String filePath;

    /**
     * @return
     */
    public FileLock getFileLock() {
        FileLock lock = null;
        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            FileChannel channel = file.getChannel();
            // 尝试获取文件锁 只锁住第一行
            lock = channel.tryLock(0L, 1L, false);
            // 如果获取不到锁，则尝试锁住第二行
            if(lock == null) {
                lock = channel.tryLock(1L, 1L, false);
            }
        } catch (Exception e) {
            log.error("[getFileLock][Exception]", e);
        }
        return lock;
    }

}