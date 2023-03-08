import { UserService } from '../model/UserService';
import { Signup } from '../actions/Signup';
import { Login } from '../actions/Login';
import { AuthService } from '../model/auth/AuthService';
import { SessionStorage } from '../model/auth/SessionStorage';
import { Post } from '../actions/Post';
import { GetCurrentUsername } from '../actions/GetCurrentUsername';
import { PostService } from '../model/PostService';
import { HttpPostService } from './http/HttpPostService';

export class ActionFactory {
    constructor(private userService: UserService, private authService: AuthService, private sessionStorage: SessionStorage, private postService: PostService) {}

    signup() {
        return new Signup(this.userService);
    }

    login() {
        return new Login(this.authService, this.sessionStorage);
    }

    createPost() {
        return new Post(this.sessionStorage, this.postService);
    }

    getCurrentUsername() {
        return new GetCurrentUsername(this.sessionStorage);
    }
}
