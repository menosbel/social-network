import { HttpClient } from './HttpClient';
import { AuthService, LoginResponse } from '../../model/auth/AuthService';

export class HttpAuthService implements AuthService {
    constructor(private httpClient: HttpClient) {
    }

    async login(username: string, password: string): Promise<LoginResponse> {
        const httpResponse = await this.httpClient.post('/login', { username, password });
        return httpResponse.body;
    }
}
