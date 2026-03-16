package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wyh
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void configureAsyncSupport (AsyncSupportConfigurer configurer) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        taskExecutor.setMaxPoolSize(threadPoolExecutor.getMaximumPoolSize());
        taskExecutor.setKeepAliveSeconds((int) threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS));
        taskExecutor.setQueueCapacity(5000);
        taskExecutor.setThreadNamePrefix("async-mvc-");
        taskExecutor.setRejectedExecutionHandler (threadPoolExecutor.getRejectedExecutionHandler());
        taskExecutor.initialize();
        configurer.setTaskExecutor(taskExecutor) ;
        // 30秒 超时
        configurer.setDefaultTimeout(30000L);
    }
}
