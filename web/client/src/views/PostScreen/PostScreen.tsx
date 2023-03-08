import React, { Component } from 'react';
import { PostVM } from './PostVM';
import { PostView } from './PostView';
import AppContext from '../app/AppContext';
import styled from 'styled-components';

export class PostScreen extends Component<any, State> implements PostView {
    presenter = AppContext.presenters.createPost(this);

    state = {
        model: new PostVM(),
    };

    show(model: PostVM) {
        this.setState({ model });
    }
    componentDidMount() {
        this.presenter.start();
    }

    render(){
        return(
            <Container>
                <h1> hola {this.state.model.username}</h1>
                <label>Ingrese el texto a postear: </label>
                <textarea onChange={e => this.presenter.setMessage(e.target.value)}></textarea>
                <a href="#" onClick={this.presenter.createPost()}>Postear</a>
            </Container>
        );
    }
}

interface State {
    model: PostVM;
}

const Container = styled.div`
    display: flex;
    flex-direction: column;
    padding: 10vw;
`;