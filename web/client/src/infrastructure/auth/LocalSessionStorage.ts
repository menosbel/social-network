import { SessionStorage } from '../../model/auth/SessionStorage';
import { Session } from '../../model/auth/Session';

const SESSION_KEY = "social_network_kata_session";

export class LocalSessionStorage implements SessionStorage {
    get(): Session {
        const serializedSession = localStorage.getItem(SESSION_KEY);
        if (!serializedSession) return new Session();
        const snapshot = JSON.parse(serializedSession);
        return Session.fromSnapshot(snapshot);
    }

    save(session: Session) {
        const serializedSession = JSON.stringify(session.snapshot);
        localStorage.setItem(SESSION_KEY, serializedSession);
    }

}