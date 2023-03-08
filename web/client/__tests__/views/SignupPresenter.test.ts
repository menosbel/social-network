import { SignupPresenter, SignupView } from '../../src/views/SignupScreen/SignupPresenter';
import { SignupVM } from '../../src/views/SignupScreen/SignupVM';
import { anything, instance, verify, when } from 'ts-mockito';
import { mockEq } from '../lib/ts-mockito-extensions';
import { Signup } from '../../src/actions/Signup';
import { ValidationError } from '../../src/model/ValidationError';

it('starts with default username @alice', () => {
    presenter.start();

    expect(view.model?.username).toEqual('@alice');
});

it('changes username', () => {
    presenter.setUsername('new username');

    expect(view.model?.username).toEqual('new username');
});

it('changes password', () => {
    presenter.setPassword('new password');

    expect(view.model?.password).toEqual('new password');
});

it('signup should execute signup action', async () => {
    presenter.setUsername('@bob');
    presenter.setPassword('1234');

    await presenter.signup();

    verify(signup.exec('@bob', '1234')).called();
});

it('show username errors on signup', async () => {
    when(signup.exec(anything(), anything())).thenReject(new ValidationError('username', 'some username error'));

    await presenter.signup();

    expect(view.model?.errors['username']).toEqual('some username error');
});

it('show loading while doing signup', async () => {
    presenter.setUsername('@bob');
    presenter.setPassword('1234');

    await presenter.signup();

    expect(view.hasLoaded).toEqual(true);
    expect(view.model?.isLoading).toEqual(false);
});

beforeEach(() => {
    signup = mockEq<Signup>();
    view = new ViewStub();
    presenter = new SignupPresenter(view, instance(signup));
});

let view: ViewStub;
let presenter: SignupPresenter;
let signup: Signup;

class ViewStub implements SignupView {
    model: SignupVM|null = null;
    hasLoaded = false;

    show(model: SignupVM) {
        this.model = model;
        if (model.isLoading) { this.hasLoaded = true; }
    }
}
