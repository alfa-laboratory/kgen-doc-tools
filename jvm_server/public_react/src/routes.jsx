import React from 'react';
import {BrowserRouter as Router, Redirect, Route} from 'react-router-dom';
import App from './app';
import About from './components/About';
import Sample from './components/Sample';
import 'styles/index.scss';

const Routes = () => (
    <Router>
        <div>
            <Redirect path="/" to="/portal"/>
            <Route exact path="/portal" component={App}/>
            <Route path="/portal/about" component={About}/>
            <Route path="/portal/sample" component={Sample}/>
        </div>
    </Router>
);

export default Routes;
