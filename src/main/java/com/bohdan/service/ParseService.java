package com.bohdan.service;

import com.bohdan.exception.FileParsingException;
import org.jsoup.nodes.Document;

public interface ParseService {
	Document parse(String path) throws FileParsingException;
}
