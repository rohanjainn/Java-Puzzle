package rjn.project.wordsearchapi.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rjn.project.wordsearchapi.exception.WordSearchException;
import rjn.project.wordsearchapi.service.WordSearchGridService;

@RestController
@RequestMapping("/puzzle")
public class WordSearchAPIController {

	@Autowired
	WordSearchGridService wordSearchGridService;

	@GetMapping("/wordgrid/{id}")
	@ResponseBody
	public String getWordSearchGrid(@PathVariable(name = "id", required = false) Integer gridSize,
			@RequestParam String word) throws WordSearchException {

		if (null != word && word.length() > 5 && null != gridSize) {

			List<String> words = Arrays.asList(word.toUpperCase().split(","));
			char[][] grid = wordSearchGridService.createGridByWords(gridSize, words);

			String response = "";

			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					response += grid[i][j] + " ";
				}
				response += "\n";
				System.out.print(response);
			}
			System.out.print(response.toString());
			return response;
		} else
			throw new WordSearchException("Params not valid");
	}

}
