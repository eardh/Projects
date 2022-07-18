package com.eardh;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.eardh.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@Slf4j
public class DataModelApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataModelApplication.class, args);
    }

    @Async
    @Scheduled(cron = "0 0/8 * * * ?")
    public void scheduleEvent() {
        File dir = new File(Constant.CLASSPATH + "/temp");
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                String name = file.getName();
                long l = Long.parseLong(name.substring(name.lastIndexOf("_") + 1, name.indexOf(".")));
                LocalDateTime now = LocalDateTimeUtil.now();
                LocalDateTime time = LocalDateTimeUtil.of(l);
                long between = LocalDateTimeUtil.between(time, now, ChronoUnit.MINUTES);
                if (between > 10) {
                    log.info("清理零时文件：{}", name);
                    FileUtil.del(file);
                }
            }
        }
    }
}
