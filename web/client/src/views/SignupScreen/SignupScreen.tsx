import React from 'react';
import signupImage from './signup.png';
import pepe from './pepe.jpg';
import { SignupView } from './SignupPresenter';
import { SignupVM } from './SignupVM';
import AppContext from '../app/AppContext';
import { TextField } from '../components/TextField';
import styled from 'styled-components';

export class SignupScreen extends React.Component<any, State> implements SignupView {
    presenter = AppContext.presenters.signup(this);

    state = {
        model: new SignupVM(),
    };

    componentDidMount() {
        this.presenter.start();
    }

    show(model: SignupVM) {
        this.setState({ model });
    }

    render() {
        if (this.state.model.isLoading) return <div>Loading....</div>;
        return <div>
            <Title>
                Signup
                <span>Subtitle</span>
                <img src={signupImage} />
            </Title>
            <StyledTextField
                label="Username:"
                value={this.state.model.username}
                onChange={value => this.presenter.setUsername(value) }
                error={this.state.model.errors['username']}
            />
            <StyledTextField
                label="Password:"
                value={this.state.model.password}
                inputType="password"
                onChange={value => this.presenter.setPassword(value) }
                error={this.state.model.errors['password']}
            />
            <a href="#" onClick={() => this.presenter.signup()}>Signup</a>
            <img src={pepe} />
        </div>;
    }
}

const Title = styled.div`
  font-size: 20px;
  font-weight: bold;
  
  span { color: blue; }
`;

const StyledTextField = styled(TextField)`
  border: 1px solid #000000;
  padding: 10px;
`;

interface State {
    model: SignupVM;
}
