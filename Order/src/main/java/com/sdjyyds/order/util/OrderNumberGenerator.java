package com.sdjyyds.order.util;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
@Component
public class OrderNumberGenerator {
    private static final Random RANDOM = new Random();

    public static String generateOrderNumber() {
        // 时间戳部分（17位）：年月日时分秒毫秒
        String timePart = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        // 机器ID（可配置，2位）
        String machineId = "01";  // 假设当前机器编号为 01

        // 随机数部分（11位）
        String randomPart = String.format("%011d", Math.abs(RANDOM.nextLong()) % 1_000_000_00000L);

        return timePart + machineId + randomPart;  // 共30位
    }
}
