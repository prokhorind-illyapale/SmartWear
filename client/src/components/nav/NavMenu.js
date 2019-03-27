import React, { Component } from 'react';
import '../../styleForComponents/NavMenu.css';
import { connect } from 'react-redux';
import { Link } from "react-router-dom";
import {bindActionCreators} from "redux";
import {logOut} from "../../actions/logOut";
import { slide as Menu } from 'react-burger-menu';
import {Icon} from 'semantic-ui-react';

const styles = {
    bmBurgerButton: {
        position: 'fixed',
        width: '36px',
        height: '30px',
        left: '36px',
        top: '36px'
    },
    bmBurgerBars: {
        background: '#373a47'
    },
    bmBurgerBarsHover: {
        background: '#a90000'
    },
    bmCrossButton: {
        height: '24px',
        width: '24px'
    },
    bmCross: {
        background: '#bdc3c7'
    },
    bmMenuWrap: {
        position: 'fixed',
        height: '100%'
    },
    bmMenu: {
        background: '#373a47',
        padding: '2.5em 1.5em 0',
        fontSize: '1.15em'
    },
    bmMorphShape: {
        fill: '#373a47'
    },
    bmItemList: {
        color: '#b8b7ad',
        padding: '0.8em'
    },
    bmItem: {
        display: 'inline-block'
    },
    bmOverlay: {
        background: 'rgba(0, 0, 0, 0.3)'
    }
};

class NavMenu  extends Component {

    logOut = () => {
        if(window.confirm("Are you sure?")) {
            this.props.logOut();
        }
        this.props.closeMenu()
    };


    render() {
        let data = this.props.data.userData;
        return (
            <div>
                <Menu
                    id='slide'
                    styles={styles}
                    right
                    width={ '20%' }
                    customBurgerIcon={ false }
                    isOpen={this.props.isOpen}
                    onStateChange={(state) => this.props.handleStateChange(state)}
                >
                        <Link to={`/`} onClick={this.props.closeMenu}>
                            <Icon name='home'/>
                            <span>Home</span>
                        </Link>
                        <Link to={`/looks`} onClick={this.props.closeMenu}>
                            <Icon name='user circle'/>
                            <span>Looks</span>
                        </Link>
                        <Link to={`/settings`} onClick={this.props.closeMenu}>
                            <Icon name='settings'/>
                            <span>Settings</span>
                        </Link>
                        {this.props.isOpen === true
                        && data.userRole.roleName === "ADMIN"
                        &&
                        <Link  to={`/admin`} onClick={this.props.closeMenu}>
                            <Icon name='privacy'/>
                            <span>Admin</span>
                        </Link>
                        }
                        <Link to={`/`} onClick={this.logOut}>
                            <Icon name='log out'/>
                            <span>Logout</span>
                        </Link>
                </Menu>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        data: state.appData
    }
}

function matchDispatchToProps(dispath) {
    return bindActionCreators( { logOut: logOut }, dispath);
}

export default connect(mapStateToProps, matchDispatchToProps)(NavMenu);