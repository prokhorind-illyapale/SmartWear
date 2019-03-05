import React, { Component } from 'react';
import '../../styleForComponents/AuthPage.css';
import SignIn from "./SignIn";
import SignUp from "./SignUp";
import { Route} from "react-router-dom";

class AuthPage extends Component {

    render() {
        return (
            <div className='auth-body'>
                <Route exact path="/" component={SignIn}/>
                <Route path="/register" component={SignUp}/>
            </div>
        )
    }

}


export default AuthPage