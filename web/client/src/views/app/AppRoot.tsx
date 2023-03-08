import { LoginScreen } from '../LoginScreen/LoginScreen';
import { SignupScreen } from '../SignupScreen/SignupScreen';
import React from 'react';
import { BrowserRouter, Switch, Route, Link } from 'react-router-dom';
import { PostScreen } from '../PostScreen/PostScreen';

export const AppRoot = () => (
    <BrowserRouter>
        <h1>Social Network</h1>
        <nav>
            <Link to="/login">Login </Link>
            <Link to="/signup">Signup </Link>
            <Link to="/post">Post </Link>
        </nav>
        <Switch>
            <Route path="/login" component={LoginScreen} />
            <Route path="/signup" component={SignupScreen} />
            <Route path="/post" component={PostScreen} />
            <Route path="/">
                Home
            </Route>
        </Switch>
    </BrowserRouter>
);
