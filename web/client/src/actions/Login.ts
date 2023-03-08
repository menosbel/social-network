import { AuthService } from '../model/auth/AuthService';
import { SessionStorage } from '../model/auth/SessionStorage';
import { InvalidCredentialsError } from '../model/auth/InvalidCredentialsError';
import { ValidationError } from '../model/ValidationError';

export class Login {
    constructor(private authService: AuthService, private sessionStorage: SessionStorage) {
    }

    async exec(username: string, password: string) {
        try {
            await this.doLogin(username, password);
        } catch (e) {
            this.fail(e);
        }
    }

    private async doLogin(username: string, password: string) {
        const loginResponse = await this.authService.login(username, password);
        const session = this.sessionStorage.get();
        session.authenticate(username, loginResponse.sessionToken);
        this.sessionStorage.save(session);
    }

    private fail(e) {
        if (e instanceof InvalidCredentialsError) {
            throw new ValidationError('', 'Invalid Credentials');
        }
        throw e;
    }
}