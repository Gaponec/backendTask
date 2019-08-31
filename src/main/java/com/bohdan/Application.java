package com.bohdan;

import com.bohdan.exception.FileParsingException;
import com.bohdan.service.ElementService;
import com.bohdan.service.ParseService;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.String.format;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger LOG = Logger.getLogger(Application.class);

	@Value("${application.element.id}")
	private String targetId;

	@Autowired
	@Qualifier("htmlParseService")
	private ParseService parseService;

	@Autowired
	@Qualifier("htmlElementService")
	private ElementService elementService;

	static {
		BasicConfigurator.configure();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		if (isRequiredArgumentsPresent(args)) {
			try {
				LOG.info(parseDocumentsAndFindMostSimilarElement(args[0], args[1]));
			} catch (FileParsingException e) {
				LOG.error("Error occur during parsing files", e);
			}
		} else {
			LOG.error(format("Wrong number of arguments %d.", args.length));
		}
	}

	private String parseDocumentsAndFindMostSimilarElement(String pathToSourceDoc, String pathToTargetDoc) throws FileParsingException {
		Document sourceDoc = parseService.parse(pathToSourceDoc);
		Document targerDoc = parseService.parse(pathToTargetDoc);

		Element element = sourceDoc.getElementById(targetId);

		if (element != null) {
			return elementService.getElementAbsolutePath(elementService.findMostSimilarElementInDocument(element, targerDoc));
		} else {
			LOG.error(format("Could not find element in source file with id %s", targetId));
			return "";
		}
	}

	private boolean isRequiredArgumentsPresent(String[] args) {
		return args.length >= 2;
	}
}
