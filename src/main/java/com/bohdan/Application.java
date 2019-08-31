package com.bohdan;

import com.bohdan.exception.FileParsingException;
import com.bohdan.service.ElementService;
import com.bohdan.service.ParseService;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Value("${application.element.id}")
	private String targerId;

	@Autowired
	@Qualifier("htmlParseService")
	private ParseService parseService;

	@Autowired
	@Qualifier("htmlElementService")
	private ElementService elementService;


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		if (isRequiredArgumentsPresent(args)) {
			try {
				Document document1 = parseService.parse(args[0]);
				Document document2 = parseService.parse(args[1]);

				Element element = document1.getElementById(targerId);

				System.out.println(elementService.getElementAbsolutePath(elementService.findMostSimilarElementInDocument(element, document2)));
			} catch (FileParsingException e) {
				return;
			}
		}
	}

	private boolean isRequiredArgumentsPresent(String[] args) {
		return args.length >= 2;
	}
}
