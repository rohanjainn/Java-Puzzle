package rjn.project.wordsearchapi.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import rjn.project.wordsearchapi.exception.WordSearchException;
import rjn.project.wordsearchapi.model.Coordinates;

@Service
public class WordSearchGridService {


	
	private enum Direction {
		HORIZONTAL, VERTICAL, DIAGONAL,INVERSE_HORIZONTAL, INVERSE_VERTICAL, INVERSE_DIAGONAL
	};

	

	
	/*
	 * BiFunction<String, Coordinates, Direction> getDirection = (word, coordinate)
	 * -> {
	 * 
	 * List<Direction> directions = Arrays.asList(Direction.values());
	 * Collections.shuffle(directions);
	 * 
	 * for (Direction direction : directions) { if (doesFit(word, coordinate,
	 * direction)) { return direction; } }
	 * 
	 * return null; };
	 */

	/*
	 * BiPredicate<String, Coordinates> doesFit = (word, coordinate) -> {
	 * 
	 * List<Direction> directions = Arrays.asList(Direction.values());
	 * Collections.shuffle(directions);
	 * 
	 * return false; };
	 */

	private Direction getDirection(String word,Coordinates coordinate,int gridSize,char[][]gridContents) {

		List<Direction> directions = Arrays.asList(Direction.values());
		Collections.shuffle(directions);

		for (Direction direction : directions) {
			if (doesFit(word, coordinate, direction,gridSize,gridContents)) {
				return direction;
			}
		}

		return null;
	};
	
	private boolean doesFit(String word, Coordinates coordinate, Direction direction,int gridSize,char[][] gridContents) {
		List<Direction> directions = Arrays.asList(Direction.values());
		Collections.shuffle(directions);

		int wordLength = word.length();
		switch(direction) {
			
		case HORIZONTAL: 
			if(coordinate.getY()+wordLength>gridSize) return false;
			for(int i=0;i<wordLength;i++) {
				if(gridContents[coordinate.getX()][coordinate.getY()+i]!='*') return false;
			}
			break;
		case VERTICAL:
			if(coordinate.getX()+wordLength>gridSize) return false;
			for(int i=0;i<wordLength;i++) {
				if(gridContents[coordinate.getX()+i][coordinate.getY()]!='*') return false;
			}
			break;
		case DIAGONAL:
			if(coordinate.getX()+wordLength>gridSize||coordinate.getY()+wordLength>gridSize) return false;
			for(int i=0;i<wordLength;i++) {
				if(gridContents[coordinate.getX()+i][coordinate.getY()+i]!='*') return false;
			}
			break;
			
			///
			
			
		case INVERSE_HORIZONTAL: 
			if(coordinate.getY()<wordLength) return false;
			for(int i=0;i<wordLength;i++) {
				if(gridContents[coordinate.getX()][coordinate.getY()-i]!='*') return false;
			}
			break;
		case INVERSE_VERTICAL:
			if(coordinate.getX()<wordLength) return false;
			for(int i=0;i<wordLength;i++) {
				if(gridContents[coordinate.getX()-i][coordinate.getY()]!='*') return false;
			}
			break;
		case INVERSE_DIAGONAL:
			if(coordinate.getX()<wordLength||coordinate.getY()<wordLength) return false;
			for(int i=0;i<wordLength;i++) {
				if(gridContents[coordinate.getX()-i][coordinate.getY()-i]!='*') return false;
			}
			break;
		}
		return true;
	}

	
	public void displayGrid(int gridSize,char[][] gridContents) {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				System.out.print(gridContents[i][j] + " ");
			}
			System.out.println();
		}
	}

	public char[][] createGridByWords(int gridSize,List<String> words) throws WordSearchException {
		List<Coordinates> coordinateslist=new ArrayList<>();
		char[][] gridContents=new char[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				coordinateslist.add(new Coordinates(i, j));
				gridContents[i][j] = '*';
			}
		}
		if (!words.isEmpty()) {
			Collections.shuffle(coordinateslist);
			for (String w : words) {
				if (w.length() > gridSize)
					throw new WordSearchException("Entered word is greater than grid size,which can't be fit");
				else {

					for (Coordinates cord : coordinateslist) {

						int x = cord.getX();
						int y = cord.getY();
						Direction selectedDirection=getDirection(w,cord,gridSize,gridContents);
						if (null!=selectedDirection) {
							
							switch(selectedDirection) {
							
							case HORIZONTAL:
								for (char c : w.toCharArray()) {
									gridContents[x][y++] = c;
								}
								break;
							
							case VERTICAL:
								for (char c : w.toCharArray()) {
									gridContents[x++][y] = c;
								}
								break;
							case DIAGONAL:
								for (char c : w.toCharArray()) {
									gridContents[x++][y++] = c;
								}
								break;
								
							case INVERSE_HORIZONTAL:
								for (char c : w.toCharArray()) {
									gridContents[x][y--] = c;
								}
								break;
							
							case INVERSE_VERTICAL:
								for (char c : w.toCharArray()) {
									gridContents[x--][y] = c;
								}
								break;
							case INVERSE_DIAGONAL:
								for (char c : w.toCharArray()) {
									gridContents[x--][y--] = c;
								}
								break;
							}
							break;
						}

						//break;
					}

				}
			}
		}
		randomFillGrid(gridSize,gridContents);
		return gridContents;
	}
	
	private void randomFillGrid(int gridSize,char[][] gridContents) {
		String letters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				
				if(gridContents[i][j]=='*') {
					int randomIndex=ThreadLocalRandom.current().nextInt(0,letters.length());
					gridContents[i][j]=letters.charAt(randomIndex);
				}
				//System.out.print(gridContents[i][j] + " ");
			}
			//System.out.println();
		}
	}
}
