package KeyboardTrainer.forms.exercise;


import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;


public class GodlikeVisualizer implements ExerciseVisualizer {
	private static final Paint  PRINTED_COLOR      = Color.gray(0.8);
	private static final Paint  CURRENT_COLOR      = Color.gray(0.0);
	private static final Paint  UNPRINTED_COLOR    = Color.gray(0.1);
	private static final String CURRENT_BACKGROUND = "hsb(90, 65%, 80%)";
	private static final String ERROR_BACKGROUND   = "hsb(2, 80%, 100%)";
	private static final int    FONT_SIZE          = 20;
	
	private final List<Text> letters    = new ArrayList<>();
	private final ScrollPane scrollPane = new ScrollPane();
	private final TextFlow   textFlow   = new TextFlow();
	private final String     text;
	
	private int currentLetterIndex;
	
	GodlikeVisualizer(String text) {
		this.text = text;
		scrollPane.setContent(textFlow);
		scrollPane.setFitToWidth(true);
		scrollPane.setOnKeyPressed(Event::consume);
		
		fillTextNodes(text);
	}
	
	private void fillTextNodes(String text) {
		Font font = new Font(FONT_SIZE);
		
		for (char c : text.toCharArray()) {
			Text e = new Text(String.valueOf(c));
			e.setFont(font);
			e.setFill(UNPRINTED_COLOR);
			letters.add(e);
		}
		
		textFlow.getChildren().addAll(letters);
	}
	
	@Override
	public void start() {
		reset();
		setLetterDesign(0, LetterDesign.CURRENT);
	}
	
	private void setLetterDesign(int letterIndex, LetterDesign design) {
		Text letter = letters.get(letterIndex);
		switch (design) {
			case UNPRINTED: {
				setLetterDesign(letter, UNPRINTED_COLOR, false);
				break;
			}
			
			case PRINTED: {
				setLetterDesign(letter, PRINTED_COLOR, false);
				break;
			}
			
			case CURRENT: {
				setLetterDesign(letter, CURRENT_COLOR, true);
				setCurrentLetterBackground(letter, CURRENT_BACKGROUND);
				setCurrentLetterScroll(letter);
				break;
			}
			
			case CURRENT_BAD: {
				setCurrentLetterBackground(letter, ERROR_BACKGROUND);
				break;
			}
		}
	}
	
	private void setLetterDesign(Text letter, Paint color, boolean underline) {
		letter.setFill(color);
		letter.setUnderline(underline);
	}
	
	/**
	 * Устанавливает цвет фона ТОЛЬКО ДЛЯ ОДНОЙ буквы.
	 * При повторном вызове предыдущий установленный фон сбросится.
	 */
	private void setCurrentLetterBackground(Text letter, String color) {
		final Bounds        out      = textFlow.getBoundsInLocal();
		final StringBuilder sbInsets = new StringBuilder();
		
		Bounds b = letter.getBoundsInParent();
		sbInsets.append(b.getMinY()).append(" ");
		sbInsets.append(out.getMaxX() - b.getMaxX() - 1).append(" ");
		sbInsets.append(out.getMaxY() - b.getMaxY()).append(" ");
		sbInsets.append(b.getMinX());
		
		textFlow.setStyle("-fx-background-color: "
		                  + color
		                  + "; -fx-background-insets: "
		                  + sbInsets.toString()
		                  + ";");
	}
	
	private void setCurrentLetterScroll(Text letter) {
		Bounds bounds       = letters.get(letters.size() - 1).getBoundsInParent();
		double letterHeight = bounds.getMaxY() - bounds.getMinY();
		double scrollHeight = scrollPane.getContent().getBoundsInLocal().getHeight();
		
		Bounds b = letter.getBoundsInParent();
		double
				scroll =
				(b.getMaxY() - (1 - (b.getMaxY() - letterHeight) / scrollHeight) * letterHeight) / scrollHeight; // fu
		scrollPane.setVvalue(scroll);
	}
	
	@Override
	public void handleGoodKey() {
		if (currentLetterIndex >= text.length()) {
			return;
		}
		
		setLetterDesign(currentLetterIndex, LetterDesign.PRINTED);
		textFlow.setStyle("");
		currentLetterIndex++;
		if (currentLetterIndex < text.length()) {
			setLetterDesign(currentLetterIndex, LetterDesign.CURRENT);
		}
	}
	
	@Override
	public void handleBadKey() {
		if (currentLetterIndex >= text.length()) {
			return;
		}
		
		setLetterDesign(currentLetterIndex, LetterDesign.CURRENT_BAD);
	}
	
	@Override
	public void reset() {
		for (int i = 0; i < letters.size(); i++) {
			setLetterDesign(i, LetterDesign.UNPRINTED);
		}
		currentLetterIndex = 0;
		textFlow.setStyle("");
		scrollPane.setVvalue(0);
	}
	
	@Override
	public Region getRegion() {
		return scrollPane;
	}
	
	
	private enum LetterDesign {
		UNPRINTED,
		PRINTED,
		CURRENT,
		CURRENT_BAD
	}
}
