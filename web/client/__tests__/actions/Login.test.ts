import { anything, instance, when } from 'ts-mockito';
import { alice } from '../model/UserExamples';
import { Login } from '../../src/actions/Login';
import { mockEq } from '../lib/ts-mockito-extensions';
import { AuthService } from '../../src/model/auth/AuthService';
import { InMemorySessionStorage } from '../../src/infrastructure/auth/InMemorySessionStorage';
import { expectThrows } from '../lib/expectThrows';
import { InvalidCredentialsError } from '../../src/model/auth/InvalidCredentialsError';
import { ValidationError } from '../../src/model/ValidationError';

it('should authenticate user in auth service', async () => {
    when(authService.login(alice.username, alice.password)).thenResolve({ sessionToken: aliceSessionToken });

    await login.exec(alice.username, alice.password);

    const currentSession = sessionStorage.get();
    expect(currentSession.sessionToken).toEqual(aliceSessionToken);
});

it('should return error with invalid credentials', async () => {
    when(authService.login(anything(), anything())).thenReject(new InvalidCredentialsError());

    await expectThrows(async () => {
        await login.exec(alice.username, 'invalid password');
    }, ValidationError);
});

beforeEach(() => {
    authService = mockEq<AuthService>();
    sessionStorage = new InMemorySessionStorage();
    login = new Login(instance(authService), sessionStorage);
});

const aliceSessionToken = '123nlkjb';
let sessionStorage;
let authService: AuthService;
let login: Login;