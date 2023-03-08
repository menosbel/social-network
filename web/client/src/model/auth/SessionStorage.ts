import { Session } from './Session';

export interface SessionStorage {
    get(): Session;
    save(session: Session);
}