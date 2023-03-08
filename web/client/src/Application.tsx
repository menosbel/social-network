import './views/app/base.css';
import ReactDOM from 'react-dom';
import { AppRoot } from './views/app/AppRoot';
import React from 'react';
import { AxiosHttpClient } from './infrastructure/http/AxiosHttpClient';
import { PresenterFactory } from './views/app/PresenterFactory';
import AppContext from './views/app/AppContext';
import { Config } from './infrastructure/Config';
import { HttpUserService } from './infrastructure/http/HttpUserService';
import { ActionFactory } from './infrastructure/ActionFactory';
import { HttpAuthService } from './infrastructure/http/HttpAuthService';
import { LocalSessionStorage } from './infrastructure/auth/LocalSessionStorage';
import { HttpPostService } from './infrastructure/http/HttpPostService';

export class Application {
    private httpClient = new AxiosHttpClient(Config.get('apiBaseUrl'));
    private userService = new HttpUserService(this.httpClient);
    private authService = new HttpAuthService(this.httpClient);
    private postService = new HttpPostService(this.httpClient);
    private sessionStorage = new LocalSessionStorage();
    private actionFactory = new ActionFactory(this.userService, this.authService, this.sessionStorage, this.postService);
    private presenterFactory = new PresenterFactory(this.actionFactory);

    constructor() {
        this.initializeAppContext();
        // @ts-ignore
        window.app = this;
    }

    private initializeAppContext() {
        AppContext.presenters = this.presenterFactory;
    }

    render() {
        ReactDOM.render(<AppRoot/>, document.getElementById('root'));
    }
}
