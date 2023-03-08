export interface AuthService {
    login(username: string, password: string): Promise<LoginResponse>
}

export interface LoginResponse {
    sessionToken: string;
}
