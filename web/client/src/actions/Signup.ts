import { UserService } from '../model/UserService';
import { ValidationError } from '../model/ValidationError';

export class Signup {
    constructor(private userService: UserService) {}

    async exec(username: string, password: string) {
        this.validate(username);
        await this.userService.create(username, password);
    }

    private validate(username: string) {
        if (username.length === 0) throw new ValidationError('username', 'Debe ingresar un nombre de usuario');
    }
}
