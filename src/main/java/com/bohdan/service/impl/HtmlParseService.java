package com.bohdan.service.impl;

import com.bohdan.exception.FileParsingException;
import com.bohdan.service.ParseService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class HtmlParseService implements ParseService {

	@Value("${application.encoding}")
	private String encoding;

	@Override
	public Document parse(String path) throws FileParsingException {
		try {
			return Jsoup.parse(new File(path), encoding);
		} catch (IOException e) {
			throw new FileParsingException(e.getMessage());
		}
	}
}
