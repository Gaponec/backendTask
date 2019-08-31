package com.bohdan.service.impl;

import com.bohdan.exception.FileParsingException;
import com.bohdan.service.ParseService;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

@Component
public class HtmlParseService implements ParseService {

	private static final Logger LOG = Logger.getLogger(HtmlParseService.class);

	@Value("${application.encoding}")
	private String encoding;

	@Override
	public Document parse(String path) throws FileParsingException {
		try {
			LOG.debug(format("About to parse file with path %s", path));
			return Jsoup.parse(new File(path), encoding);
		} catch (IOException e) {
			LOG.error(format("Error occured during parsing file %s", path), e);
			throw new FileParsingException(e.getMessage());
		}
	}
}
