package ebe.P_Judakov.s.JAVABOT;

import ebe.P_Judakov.s.JAVABOT.service.interfaces.TelegramBotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = "ebe.P_Judakov.s.JAVABOT.service.jpa")
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		TelegramBotService bot = context.getBean(TelegramBotService.class);
		bot.init();
	}
}
