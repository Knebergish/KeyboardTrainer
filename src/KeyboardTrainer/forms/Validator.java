package KeyboardTrainer.forms;


import KeyboardTrainer.forms.general.AlertFormManager;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;


public class Validator {
	private final List<Checker> checkers;
	
	public Validator(List<Checker> checkers) {
		this.checkers = checkers;
	}
	
	/**
	 * @return false - значит проверка провалена.
	 */
	public boolean validateOrAlert() {
		Checker failedChecker = null;
		for (Checker checker : checkers) {
			if (!checker.check()) {
				failedChecker = checker;
				break;
			}
		}
		
		if (failedChecker != null) {
			AlertFormManager.showAlert(Alert.AlertType.ERROR, "Ошибка", failedChecker.getErrorHeader(),
			                           failedChecker.getErrorText(), null);
		}
		
		return failedChecker == null;
	}
	
	public static class Checker {
		private final BooleanSupplier  condition;
		private final String           errorHeader;
		private final String           errorText;
		private final Supplier<String> addedToText;
		
		
		public Checker(BooleanSupplier condition, String errorHeader, String errorText) {
			this(condition, errorHeader, errorText, null);
		}
		
		public Checker(BooleanSupplier condition, String errorHeader, String errorText, Supplier<String> addedToText) {
			this.condition = condition;
			this.errorHeader = errorHeader;
			this.errorText = errorText;
			this.addedToText = addedToText;
		}
		
		/**
		 * @return false - значит не прошёл проверку.
		 */
		public boolean check() {
			return condition.getAsBoolean();
		}
		
		public String getErrorHeader() {
			return errorHeader;
		}
		
		public String getErrorText() {
			if (addedToText != null) {
				return errorText + addedToText.get();
			} else {
				return errorText;
			}
		}
	}
}