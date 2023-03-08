import { HttpUserService } from '../../../src/infrastructure/http/HttpUserService';
import { HttpClient } from '../../../src/infrastructure/http/HttpClient';
import { mockEq } from '../../lib/ts-mockito-extensions';
import { instance, verify } from 'ts-mockito';
import { alice } from '../../model/UserExamples';

it('create should make a post to /users with creation data', async () => {
    await userService.create(alice.username, alice.password);

    verify(httpClient.post('/users', { username: alice.username, password: alice.password })).called();
});

beforeEach(() => {
    httpClient = mockEq<HttpClient>();
    userService = new HttpUserService(instance(httpClient));
});

let userService: HttpUserService;
let httpClient: HttpClient;
