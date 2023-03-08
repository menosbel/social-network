import { anything, instance, verify, when } from 'ts-mockito';
import { HttpClient } from '../../../src/infrastructure/http/HttpClient';
import { HttpAuthService } from '../../../src/infrastructure/http/HttpAuthService';
import { alice } from '../../model/UserExamples';
import { mockEq } from '../../lib/ts-mockito-extensions';
import { HttpResponse } from '../../../src/infrastructure/http/HttpResponse';

it('login should make post to /login with credentials', async () => {
    const body = { username: alice.username, password: alice.password };

    await authService.login(alice.username, alice.password);

    verify(httpClient.post('/login', body)).called();
});

it('should return session token', async () => {
    when(httpClient.post(anything(), anything())).thenResolve(httpResponse({ sessionToken: aliceSessionToken }));

    const response = await authService.login(alice.username, alice.password);

    expect(response.sessionToken).toEqual(aliceSessionToken);
});

beforeEach(() => {
    httpClient = mockEq<HttpClient>();
    authService = new HttpAuthService(instance(httpClient));
    when(httpClient.post('/login', anything())).thenResolve(LOGIN_OK);
});

function httpResponse(body: any) {
    return new HttpResponse(200, body);
}

let authService: HttpAuthService;
let httpClient: HttpClient;
const aliceSessionToken = "23u8nk";
const LOGIN_OK = new HttpResponse(200, { sessionToken: aliceSessionToken });

