package com.bohdan.exception;

import java.io.IOException;

public class FileParsingException extends IOException {
	public FileParsingException(String message) {
		super(message);
	}
}
