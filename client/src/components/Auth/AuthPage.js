import React, { Component } from 'react';
import '../../styleForComponents/AuthPage.css';
import SignIn from "./SignIn";
import SignUp from "./SignUp";

class AuthPage extends Component {

    render() {
        return (
            <div className='authBody'>
                {this.props.signin === true && <SignIn/>}
                {this.props.signup === true && <SignUp/>}
            </div>
        )
    }

}


export default AuthPage