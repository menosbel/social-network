import { SessionStorage } from '../model/auth/SessionStorage';

export class GetCurrentUsername {
    constructor(private sessionStorage: SessionStorage) {

    }

    exec(): string {
        return this.sessionStorage.get().snapshot.username || '';
    }
}