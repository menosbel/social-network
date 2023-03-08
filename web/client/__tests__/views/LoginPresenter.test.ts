import { LoginVM } from '../../src/views/LoginScreen/LoginVM';
import { LoginPresenter, LoginView } from '../../src/views/LoginScreen/LoginPresenter';
import { anything, instance, verify, when } from 'ts-mockito';
import { mockEq } from '../lib/ts-mockito-extensions';
import { Login } from '../../src/actions/Login';
import { ValidationError } from '../../src/model/ValidationError';

it('start with default username @', () => {
    presenter.start();

    expect(view.model?.username).toEqual("@");
});

it('changes username', () => {
    presenter.setUsername('@alice');

    expect(view.model?.username).toEqual("@alice");
});

it('changes password', () => {
    presenter.setPassword("1234");

    expect(view.model?.password).toEqual("1234");
});

it('login should execute login action',  async () => {
    presenter.setUsername('@alice');
    presenter.setPassword('1234');

    await presenter.login();

    verify(login.exec('@alice', '1234')).called();
});

it('show username error on login', async () => {
    when(login.exec(anything(), anything())).thenReject(new ValidationError('username', 'some username error'));

    await presenter.login();

    expect(view.model?.errors['username']).toEqual('some username error');
});

it('show general error on login', async () => {
    when(login.exec(anything(), anything())).thenReject(new ValidationError('', 'some general error'));

    await presenter.login();

    expect(view.model?.errors['']).toEqual('some general error');
});

beforeEach(() =>  {
    login = mockEq<Login>();
    view = new LoginStub();
    presenter = new LoginPresenter(view, instance(login));
});

let view: LoginStub;
let presenter: LoginPresenter;
let login: Login;

class LoginStub implements LoginView{
    model: LoginVM|null = null;

    show(model: LoginVM) {
        this.model = model;
    }
}

