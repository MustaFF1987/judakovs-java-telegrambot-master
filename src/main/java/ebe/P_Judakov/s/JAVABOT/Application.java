package ebe.P_Judakov.s.JAVABOT;
import ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableJpaRepositories(basePackages = "ebe.P_Judakov.s.JAVABOT.repository.interfaces")
@EnableTransactionManagement

@ComponentScan({"ebe.P_Judakov.s.JAVABOT.repository.interfaces",
		"ebe.P_Judakov.s.JAVABOT.domen.entity.interfaces",
		"ebe.P_Judakov.s.JAVABOT.service.interfaces",
		"ebe.P_Judakov.s.JAVABOT.service.jpa",
		"ebe.P_Judakov.s.JAVABOT.scheduler"})
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		TelegramBotService bot = context.getBean(TelegramBotService.class);
		bot.init();
	}
}

