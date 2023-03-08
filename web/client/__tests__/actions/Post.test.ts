import { instance, verify } from 'ts-mockito';
import { mockEq } from '../lib/ts-mockito-extensions';
import { Post } from '../../src/actions/Post';
import { PostService } from '../../src/model/PostService';
import { LocalSessionStorage } from '../../src/infrastructure/auth/LocalSessionStorage';
import { InMemorySessionStorage } from '../../src/infrastructure/auth/InMemorySessionStorage';
import each from 'jest-each';
import { expectThrows } from '../lib/expectThrows';
import { ValidationError } from '../../src/model/ValidationError';

it('should post message in PostService', async () => {
    await post.exec(message);

    verify(postService.post(message)).called();
});

each([
    "", null, "   "
]).it('message is required: "%s"', async (message) => {
    await expectThrows(() => post.exec(message), ValidationError);
});

beforeEach(() => {
    postService = mockEq<PostService>();
    sessionStorage = new InMemorySessionStorage();
    post = new Post(sessionStorage, instance(postService));
    message = "sarasa";
});

let post: Post;
let postService: PostService;
let sessionStorage: LocalSessionStorage;
let message: string;