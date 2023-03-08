export interface UserService {
    create(username: string, password: string): Promise<void>;
}
