package KeyboardTrainer.forms.controllers.exercise.player;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class KeyboardVisualizer extends Pane {
	private final List<Key> keys;
	
	private final ImageView imageView;
	private final Rectangle rect;
	
	private final int    width;
	private final double keySize;
	
	KeyboardVisualizer(int width) {
		this.width = width;
		keySize = width / 15.;
		
		Image image = new Image("file:res/Klavo.jpg", width, 0, true, true);
		imageView = new ImageView(image);
		
		rect = new Rectangle();
		rect.setVisible(true);
		rect.setWidth(0);
		rect.setHeight(0);
		rect.setOpacity(0.4);
		rect.setStrokeWidth(0);
		
		this.getChildren().addAll(imageView, rect);
		this.setMinWidth(width);
		this.setWidth(width);
		this.setMaxWidth(width);
		
		keys = new ArrayList<>();
		initKeys();
	}
	
	private void initKeys() {
		List<Double> xLineStart = List.of(width * (0. / 600),
		                                  width * (60. / 600),
		                                  width * (70. / 600),
		                                  width * (90. / 600),
		                                  width * (150. / 600));
		List<Double> yLineStart = List.of(keySize * 0,
		                                  keySize * 1,
		                                  keySize * 2,
		                                  keySize * 3,
		                                  keySize * 4);
		
		List<List<List<Character>>> charsInLines = new ArrayList<>();
		
		List<List<Character>> numberLine = new ArrayList<>();
		numberLine.add(List.of('`', '~', 'ё'));
		numberLine.add(List.of('1', '!'));
		numberLine.add(List.of('2', '@', '"'));
		numberLine.add(List.of('3', '#', '№'));
		numberLine.add(List.of('4', '$', ';'));
		numberLine.add(List.of('5', '%'));
		numberLine.add(List.of('6', '^', ':'));
		numberLine.add(List.of('7', '&', '?'));
		numberLine.add(List.of('8', '*'));
		numberLine.add(List.of('9', '('));
		numberLine.add(List.of('0', ')'));
		numberLine.add(List.of('-', '_'));
		numberLine.add(List.of('=', '+'));
		
		List<List<Character>> letters1Line = new ArrayList<>();
		letters1Line.add(List.of('q', 'й'));
		letters1Line.add(List.of('w', 'ц'));
		letters1Line.add(List.of('e', 'у'));
		letters1Line.add(List.of('r', 'к'));
		letters1Line.add(List.of('t', 'е'));
		letters1Line.add(List.of('y', 'н'));
		letters1Line.add(List.of('u', 'г'));
		letters1Line.add(List.of('i', 'ш'));
		letters1Line.add(List.of('o', 'щ'));
		letters1Line.add(List.of('p', 'з'));
		letters1Line.add(List.of('[', 'х', '{'));
		letters1Line.add(List.of(']', 'ъ', '}'));
		
		List<List<Character>> letters2Line = new ArrayList<>();
		letters2Line.add(List.of('a', 'ф'));
		letters2Line.add(List.of('s', 'ы'));
		letters2Line.add(List.of('d', 'в'));
		letters2Line.add(List.of('f', 'а'));
		letters2Line.add(List.of('g', 'п'));
		letters2Line.add(List.of('h', 'р'));
		letters2Line.add(List.of('j', 'о'));
		letters2Line.add(List.of('k', 'л'));
		letters2Line.add(List.of('l', 'д'));
		letters2Line.add(List.of(';', 'ж'));
		letters2Line.add(List.of('\'', 'э'));
		letters2Line.add(List.of('\\'));
		
		List<List<Character>> letters3Line = new ArrayList<>();
		letters3Line.add(List.of('z', 'я'));
		letters3Line.add(List.of('x', 'ч'));
		letters3Line.add(List.of('c', 'с'));
		letters3Line.add(List.of('v', 'м'));
		letters3Line.add(List.of('b', 'и'));
		letters3Line.add(List.of('n', 'т'));
		letters3Line.add(List.of('m', 'ь'));
		letters3Line.add(List.of(',', 'б', '<'));
		letters3Line.add(List.of('.', 'ю', '>'));
		letters3Line.add(List.of('/', '.', '?'));
		
		charsInLines.add(numberLine);
		charsInLines.add(letters1Line);
		charsInLines.add(letters2Line);
		charsInLines.add(letters3Line);
		
		for (int i = 0; i < charsInLines.size(); i++) {
			double x = xLineStart.get(i);
			double y = yLineStart.get(i);
			
			var charsInLine = charsInLines.get(i);
			for (List<Character> characters : charsInLine) {
				keys.add(new Key(x, y, keySize, keySize, characters));
				x += keySize;
			}
		}
		
		keys.add(new Key(width * (150. / 600),
		                 width * (160. / 600),
		                 width * (250. / 600), keySize, List.of(' ')));
		keys.add(new Key(width * (510. / 600),
		                 width * (80. / 600),
		                 width * (90. / 600), keySize, List.of('\r', '\n')));
	}
	
	void drawGoodKey(Character charFromKey) {
		drawKey(charFromKey, Color.rgb(0, 225, 20));
	}
	
	void drawBadKey(Character charFromKey) {
		drawKey(charFromKey, Color.rgb(255, 10, 0));
	}
	
	private void drawKey(Character charFromKey, Color color) {
		Character character = Character.toLowerCase(charFromKey);
		Key key1 = keys.parallelStream()
		               .filter(key -> key.getChars().contains(character))
		               .findFirst().orElse(new Key(0, 0, 0, 0, List.of()));
		
		double dX = imageView.getBoundsInParent().getMinX();
		double dY = imageView.getBoundsInParent().getMinY();
		rect.setX(key1.getX() + dX);
		rect.setY(key1.getY() + dY);
		rect.setWidth(key1.getWidth());
		rect.setHeight(key1.getHeight());
		rect.setFill(color);
	}
	
	
	private static final class Key {
		private final double          X;
		private final double          Y;
		private final double          width;
		private final double          height;
		private final List<Character> chars;
		
		private Key(double X, double Y, double width, double height, List<Character> chars) {
			this.X = X;
			this.Y = Y;
			this.width = width;
			this.height = height;
			this.chars = new ArrayList<>();
			Collections.copy(this.chars, chars);
		}
		
		double getX() {
			return X;
		}
		
		double getY() {
			return Y;
		}
		
		double getWidth() {
			return width;
		}
		
		double getHeight() {
			return height;
		}
		
		List<Character> getChars() {
			return Collections.unmodifiableList(chars);
		}
	}
}
