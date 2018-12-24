package KeyboardTrainer.forms.controllers.login;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.forms.controllers.ActionHandler;

import java.util.function.Consumer;


public interface LoginController {
	void init(Consumer<User> loginUser, ActionHandler otherActionHandler);
}
