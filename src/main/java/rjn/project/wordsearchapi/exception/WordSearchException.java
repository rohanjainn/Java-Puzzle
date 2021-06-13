package rjn.project.wordsearchapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code =HttpStatus.BAD_REQUEST,reason = "check the entered string" )
public class WordSearchException extends RuntimeException{

	public WordSearchException(String message) {
		super(message);
	}
}
