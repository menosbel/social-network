import { Signup } from '../../src/actions/Signup';
import { alice } from '../model/UserExamples';
import { instance, verify } from 'ts-mockito';
import { mockEq } from '../lib/ts-mockito-extensions';
import { UserService } from '../../src/model/UserService';
import { expectThrows } from '../lib/expectThrows';
import { ValidationError } from '../../src/model/ValidationError';

it('should create user in user service', async () => {
    await signup.exec(alice.username, alice.password);

    verify(userService.create(alice.username, alice.password)).called();
});

it('fail if username is empty', async () => {
    const emptyUsername = '';

    await expectThrows(async () => {
        await signup.exec(emptyUsername, alice.password);
    }, ValidationError);
});

beforeEach(() => {
    userService = mockEq<UserService>();
    signup = new Signup(instance(userService));
});

let signup: Signup;
let userService: UserService;
