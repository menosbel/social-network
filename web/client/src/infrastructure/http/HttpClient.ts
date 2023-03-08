import { HttpResponse } from './HttpResponse';

export interface HttpClient {
    post(url: string, body: any): Promise<HttpResponse>;
}
