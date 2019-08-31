package com.bohdan.service.impl;

import com.bohdan.service.ElementService;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.max;
import static java.util.Comparator.comparing;

@Component
public class HtmlElementService implements ElementService {
	@Override
	public Element findMostSimilarElementInDocument(Element element, Document document) {
		Map<Element, Long> intersections = new HashMap<>();

		for (Element targetElement : document.getAllElements()) {
			long res = countMatch(targetElement.attributes(), element.attributes());
			intersections.put(targetElement, res);
		}

		return max(intersections.entrySet(), comparing(Map.Entry::getValue)).getKey();
	}

	@Override
	public String getElementAbsolutePath(Element element) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Element parent : element.parents()) {
			stringBuilder.insert(0, ">");
			if(parent.elementSiblingIndex() > 1) {
				stringBuilder.insert(0,"[" + parent.elementSiblingIndex() + "]");
			}
			stringBuilder.insert(0, parent.nodeName());
		}
		stringBuilder.append(element.nodeName());
		if(element.elementSiblingIndex() > 1) {
			stringBuilder.append('[');
			stringBuilder.append(element.elementSiblingIndex());
			stringBuilder.append(']');
		}
		return stringBuilder.toString();
	}

	private long countMatch(Attributes attributes, Attributes sourceAttr) {
		return attributes
				.asList()
				.stream()
				.filter(attribute -> sourceAttr.get(attribute.getKey()).equals(attribute.getValue()))
				.count();
	}
}
