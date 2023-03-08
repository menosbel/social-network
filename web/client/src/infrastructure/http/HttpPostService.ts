import { HttpClient } from './HttpClient';
import { PostService } from '../../model/PostService';

export class HttpPostService implements PostService{
    constructor(httpClient: HttpClient) {}

    post(message: string): number {
        return 0;
    }
}