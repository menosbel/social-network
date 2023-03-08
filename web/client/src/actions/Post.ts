import { SessionStorage } from '../model/auth/SessionStorage';
import { PostService } from '../model/PostService';
import { ValidationError } from '../model/ValidationError';

export class Post {
    constructor(private sessionStorage: SessionStorage, private postService: PostService) {}

    async exec(message: string): Promise<void> {
        if (message == null || message.trim().length === 0) {
            throw new ValidationError("message", "Invalid message");
        }
        this.postService.post(message);
    }
}