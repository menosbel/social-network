import { SignupVM } from './SignupVM';
import { Signup } from '../../actions/Signup';
import { ValidationError } from '../../model/ValidationError';

export interface SignupView {
    show(model: SignupVM);
}

export class SignupPresenter {
    private model = new SignupVM();

    constructor(private view: SignupView, private signupAction: Signup) {}

    start() {
        this.updateModel({});
    }

    setUsername(value: string) {
        this.updateModel({ username: value });
    }

    setPassword(value: string) {
        this.updateModel({ password: value });
    }

    async signup() {
        try {
            this.updateModel({ isLoading: true });
            await this.signupAction.exec(this.model.username, this.model.password);
        } catch (e) {
            if (e instanceof ValidationError) {
                const errors = {};
                errors[e.property] = e.message;
                this.updateModel({ errors });
            } else {
                throw e;
            }
        }
        this.updateModel({ isLoading: false });
    }

    private updateModel<K extends keyof SignupVM>(changes: Pick<SignupVM, K>) {
        this.model = Object.assign(this.model, changes);
        this.view.show(this.model);
    }
}
