package rjn.project.wordsearchapi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;


public class WordSearchGrid {

	private int gridSize;
	private char[][] gridContents;
	List<Coordinates> coordinateslist;

	private enum Direction {
		HORIZONTAL, VERTICAL, DIAGONAL,INVERSE_HORIZONTAL, INVERSE_VERTICAL, INVERSE_DIAGONAL
	};

	WordSearchGrid(int gridSize) {
		this.gridSize = gridSize;
		//this.letter=abc.charAt(ThreadLocalRandom.current().nextInt(abc.length()));
		gridContents = new char[gridSize][gridSize];
		coordinateslist = new ArrayList<Coordinates>();
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				coordinateslist.add(new Coordinates(i, j));
				gridContents[i][j] = '*';
			}
		}
	}

	
	BiFunction<String, Coordinates, Direction> getDirection = (word, coordinate) -> {

		List<Direction> directions = Arrays.asList(Direction.values());
		Collections.shuffle(directions);

		for (Direction direction : directions) {
			if (doesFit(word, coordinate, direction)) {
				return direction;
			}
		}

		return null;
	};

	/*
	 * BiPredicate<String, Coordinates> doesFit = (word, coordinate) -> {
	 * 
	 * List<Direction> directions = Arrays.asList(Direction.values());
	 * Collections.shuffle(directions);
	 * 
	 * return false; };
	 */

	private boolean doesFit(String word, Coordinates coordinate, Direction direction) {
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

	
	public void displayGrid() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				System.out.print(gridContents[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void fillGridByString(List<String> words) {
		if (!words.isEmpty()) {
			Collections.shuffle(coordinateslist);
			for (String w : words) {
				if (w.length() > gridSize)
					throw new RuntimeException("Entered word is greater than grid size,which can't be fit");
				else {

					for (Coordinates cord : coordinateslist) {

						int x = cord.getX();
						int y = cord.getY();
						Direction selectedDirection=getDirection.apply(w,cord);
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
		randomFillGrid();
	}
	
	private void randomFillGrid() {
		String letters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				
				if(gridContents[i][j]=='*') {
					int randomIndex=ThreadLocalRandom.current().nextInt(0,letters.length());
					gridContents[i][j]=letters.charAt(randomIndex);
				}
				System.out.print(gridContents[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}
