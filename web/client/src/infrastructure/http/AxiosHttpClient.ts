import { HttpClient } from './HttpClient';
import axios from 'axios';
import { HttpResponse } from './HttpResponse';

export class AxiosHttpClient implements HttpClient {
    constructor(private baseUrl: string) {}

    async post(url: string, body: any): Promise<HttpResponse> {
        const response = await axios.post(this.baseUrl + url, body);
        return new HttpResponse(response.status, response.data);
    }
}
