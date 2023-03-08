import { HttpClient } from './HttpClient';
import { UserService } from '../../model/UserService';

export class HttpUserService implements UserService {
    constructor(private httpClient: HttpClient) {}

    async create(username: string, password: string) {
        await this.httpClient.post('/users', { username, password });
    }
}
