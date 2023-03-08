import { mockEq } from '../lib/ts-mockito-extensions';
import { instance, verify, when } from 'ts-mockito';
import { Post } from '../../src/actions/Post';
import { PostPresenter } from '../../src/views/PostScreen/PostPresenter';
import { PostView } from '../../src/views/PostScreen/PostView';
import { PostVM } from '../../src/views/PostScreen/PostVM';
import { GetCurrentUsername } from '../../src/actions/GetCurrentUsername';

it('start with logged user username', () => {
    when(getCurrentUsername.exec()).thenReturn('@alice');

    presenter.start();

    expect(view.model?.username).toEqual('@alice');
});

it('changes message', () => {
    presenter.setMessage("hola ke ace");

    expect(view.model?.message).toEqual("hola ke ace");
});

it('post should execute post action', async () => {
    presenter.setMessage("lala");

    await presenter.createPost();

    verify(post.exec("lala")).called();

});

beforeEach(() => {
    post = mockEq<Post>();
    getCurrentUsername = mockEq<GetCurrentUsername>();
    view = new PostStub();
    presenter = new PostPresenter(view, instance(post), instance(getCurrentUsername));
});

let view: PostStub;
let presenter: PostPresenter;
let post: Post;
let getCurrentUsername: GetCurrentUsername;

class PostStub implements PostView {
    model: PostVM | null = null;

    show(model: PostVM) {
        this.model = model;
    }
}