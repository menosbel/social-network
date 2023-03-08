import { LoginVM } from './LoginVM';
import { Login } from '../../actions/Login';
import { ValidationError } from '../../model/ValidationError';

export interface LoginView {
    show(model: LoginVM);
}

export class LoginPresenter {
    private model = new LoginVM();

    constructor(private view: LoginView, private loginAction: Login) {
    }

    start() {
        this.updateModel({});
    }

    setUsername(value: string) {
        this.updateModel({ username: value });
    }

    setPassword(value: string) {
        this.updateModel({ password: value });
    }

    private updateModel<K extends keyof LoginVM>(changes: Pick<LoginVM, K>) {
        this.model = Object.assign(this.model, changes);
        this.view.show(this.model);
    }

    async login() {
        try {
            await this.loginAction.exec(this.model.username, this.model.password);
        } catch (e) {
            if (e instanceof ValidationError) {
                const errors = {};
                errors[e.property] = e.message;
                this.updateModel({ errors });
            } else {
                throw e;
            }
        }
    }
}