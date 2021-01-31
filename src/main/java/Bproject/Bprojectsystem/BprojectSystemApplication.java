package Bproject.Bprojectsystem;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// Spring Boot 2.x

@SpringBootApplication
@EnableJms
@EnableAsync
public class BprojectSystemApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BprojectSystemApplication.class, args);

	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<BprojectSystemApplication> applicationClass = BprojectSystemApplication.class;

	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
		daap.setProxyTargetClass(true);
		return daap;
	}
	@EnableAsync
	public static class AppConfig {
		@Bean
		public TaskExecutor taskExecutor() {
			ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
			taskExecutor.setMaxPoolSize(2);
			taskExecutor.setQueueCapacity(200);
			taskExecutor.afterPropertiesSet();
			return taskExecutor;
		}
	}
}