import React, { Component } from 'react';
import '../../styleForComponents/Navbar.css';
import { Button } from 'semantic-ui-react';
import { Link } from "react-router-dom";
import NavMenu from "./NavMenu";

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
                        {!this.props.token &&
                        <div className="rd__button-group rd__button-group--no-shadow">
                            <Link to={`/`}>
                                <Button basic color='teal'>Sign in</Button>
                            </Link>
                        </div>
                        }
                        {!this.props.token &&
                        <div className="rd__button-group rd__button-group--no-shadow">
                            <Link to={`/register`}>
                                <Button basic color='blue'>Sign up</Button>
                            </Link>
                        </div>
                        }
                        {this.props.token &&
                        <NavMenu/>
                        }
                    </div>
                </div>
            </div>
        );
    }
}

export default Navbar;