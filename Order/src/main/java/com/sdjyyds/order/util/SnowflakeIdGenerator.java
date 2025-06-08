package com.sdjyyds.order.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 雪花算法ID生成器
 */
@Component
public class SnowflakeIdGenerator {
    // 起始时间戳（2023-01-01）
    private static final long START_TIMESTAMP = 1672531200000L;

    // 机器ID所占位数
    private static final long WORKER_ID_BITS = 5L;
    // 数据中心ID所占位数
    private static final long DATACENTER_ID_BITS = 5L;
    // 序列号所占位数
    private static final long SEQUENCE_BITS = 12L;

    // 最大机器ID (2^5 - 1)
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    // 最大数据中心ID (2^5 - 1)
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    // 机器ID向左移12位
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    // 数据中心ID向左移17位(12+5)
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    // 时间戳向左移22位(5+5+12)
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    // 序列号掩码 (4095)
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    @Value("${snowflake.worker-id:5}")
    private long workerId;

    @Value("${snowflake.datacenter-id:5}")
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    /**
     * 无参构造器
     */
    public SnowflakeIdGenerator() {
        // 可以在初始化时做默认值检查或赋值
    }

    /**
     * 构造函数
     * @param workerId 机器ID (0-31)
     * @param datacenterId 数据中心ID (0-31)
     */
    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 生成下一个ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards. Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行序列号自增
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 序列号超出范围，等待下一毫秒
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，序列号重置
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 组合各部分生成最终ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 阻塞到下一毫秒，直到获得新的时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回当前时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
