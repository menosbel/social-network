import { PostView } from './PostView';
import { Post } from '../../actions/Post';
import { PostVM } from './PostVM';
import { GetCurrentUsername } from '../../actions/GetCurrentUsername';

export class PostPresenter {
    constructor(private view: PostView, private postAction: Post, private getCurrentUsernameAction: GetCurrentUsername) {}

    private model = new PostVM();

    start() {
        const currentUsername = this.getCurrentUsernameAction.exec();
        this.updateModel({ username: currentUsername });
    }

    createPost() {
        this.postAction.exec(this.model.message);
    }

    private updateModel<K extends keyof PostVM>(changes: Pick<PostVM, K>) {
        this.model = Object.assign(this.model, changes);
        this.view.show(this.model);
    }

    setMessage(message: string) {
        this.updateModel({ message: message });
    }
}
