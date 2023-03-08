import React, { Component } from 'react';
import { TextField } from '../components/TextField';
import { LoginVM } from './LoginVM';
import { LoginView } from './LoginPresenter';
import AppContext from '../app/AppContext';

export class LoginScreen extends Component<any, State> implements LoginView{
    private presenter = AppContext.presenters.login(this);

    state = {
        model: new LoginVM(),
    };

    show(model: LoginVM) {
        this.setState({ model });
    }

    componentDidMount() {
        this.presenter.start();
    }

    render() {
        return (
            <div>
                <TextField
                    label="Username"
                    value={this.state.model.username}
                    onChange={value => this.presenter.setUsername(value)}
                />
                <TextField
                    label="Password"
                    inputType="password"
                    value={this.state.model.password}
                    onChange={value => this.presenter.setPassword(value)}
                />
                <a href="#" onClick={() => this.presenter.login()}>Login</a>
            </div>
        );
    }
}

interface State {
    model: LoginVM;
}