import { SignupPresenter, SignupView } from '../SignupScreen/SignupPresenter';
import { ActionFactory } from '../../infrastructure/ActionFactory';
import { LoginPresenter, LoginView } from '../LoginScreen/LoginPresenter';
import { PostView } from '../PostScreen/PostView';
import { PostPresenter } from '../PostScreen/PostPresenter';

export class PresenterFactory {
    constructor(private actionFactory: ActionFactory) {}

    signup(view: SignupView): SignupPresenter {
        return new SignupPresenter(view, this.actionFactory.signup());
    }

    login(view: LoginView): LoginPresenter {
        return new LoginPresenter(view, this.actionFactory.login());
    }

    createPost(view: PostView): PostPresenter {
        return new PostPresenter(view, this.actionFactory.createPost(), this.actionFactory.getCurrentUsername());
    }
}
