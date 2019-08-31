package com.bohdan.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public interface ElementService {
	Element findMostSimilarElementInDocument(Element element, Document document);

	String getElementAbsolutePath(Element element);
}
