package com.be.code.ping

import com.be.code.ping.util.FileLockUtils
import spock.lang.Specification

import java.nio.channels.FileLock

class FileLockTest extends Specification {

    def fileLockUtils = new FileLockUtils()


    def setup() {
        fileLockUtils.filePath = "F:\\file-lock.txt"
    }

    def "getFileLock should return lock when first line locked successfully"() {
        when:
        FileLock result = fileLockUtils.getFileLock()

        then:
        result.position() == 0
    }
}