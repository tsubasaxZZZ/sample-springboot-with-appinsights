package com.example.demo;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@RestController
public class DemoApplication {
	private static final Logger logger = LogManager.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String displayHeaders(@RequestHeader Map<String, String> headers) {

		logger.info("This is an info message");
		logger.error("This is an error message");

		long startTime = System.currentTimeMillis();

		// Sleep for random second to simulate a slow endpoint
		try {
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		StringBuilder html = new StringBuilder("<table border='1'>");

		html.append("<tr><th>Header Name</th><th>Header Value</th></tr>");
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			html.append("<tr><td>").append(entry.getKey()).append("</td><td>").append(entry.getValue())
					.append("</td></tr>");
		}
		html.append("</table>");

		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;

		AccessLogger.log(logger, "/hello", 200, responseTime);

		return html.toString();
	}

	@GetMapping("/")
	public String pong() {
		return String.format("Pong", (Object[]) null);
	}
}
