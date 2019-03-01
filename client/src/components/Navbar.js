import React, { Component } from 'react';
import '../styleForComponents/Navbar.css';
import { Button } from 'semantic-ui-react'

class Navbar  extends Component {


    render() {
        return (
            <div className="navbar">
                <div className="navbar__container">
                    <div className="navbar__container__item">
                        <div className="navbar__navigation">
                            <div className="navbar__navigation__item navbar__u-hidden-on-mobile rd__button rd__button--transparent">SmartWear</div>
                        </div>
                    </div>
                    <div className="navbar__container__item">
                        <div className="rd__button-group rd__button-group--no-shadow">
                            <Button basic color='teal' onClick={this.props.signIn}>Sign in</Button>
                        </div>
                        <div className="rd__button-group rd__button-group--no-shadow">
                            <Button basic color='blue' onClick={this.props.signUp}>Sign up</Button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Navbar;