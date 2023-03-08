import { SessionStorage } from '../../model/auth/SessionStorage';
import { Session } from '../../model/auth/Session';

export class InMemorySessionStorage implements SessionStorage {
    private session = new Session();

    get(): Session {
        return this.session;
    }

    save(session: Session) {
        this.session = session;
    }
}
