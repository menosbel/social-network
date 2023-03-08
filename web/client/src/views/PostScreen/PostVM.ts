export class PostVM {
    username = '';
    message = '';
    sessionToken = '';
    isLoading = false;
    errors: { [name: string]: string } = {};
}