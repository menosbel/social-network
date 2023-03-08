export class Session {
    private sessionToken: string|null = null;
    private username: string|null = null;

    authenticate(username: string, sessionToken: string) {
        this.username = username;
        this.sessionToken = sessionToken;
    }

    get snapshot() {
        return {
            sessionToken: this.sessionToken,
            username: this.username
        };
    }

    static fromSnapshot(snapshot: any) {
        const session = new Session();
        session.sessionToken = snapshot.sessionToken;
        session.username = snapshot.username;
        return session;
    }
}
