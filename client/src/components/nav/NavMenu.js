import React, { Component } from 'react';
import { Button, Icon,  Menu } from 'semantic-ui-react';
import '../../styleForComponents/NavMenu.css';
import { connect } from 'react-redux';
import { withRouter } from "react-router-dom";
import {bindActionCreators} from "redux";
import {logOut} from "../../actions/logOut";

class NavMenu  extends Component {

    state = {
        icon: 'bars',
        menuIsShow: false,
    };

    handleItemClick = (e, { name }) => {
        this.setState({ activeItem: name });
        this.showMenu()
    } ;

    showMenu = () => {
       this.state.icon === 'bars'
           ?
           this.setState({...this.state, icon: 'cancel', menuIsShow: true})
           :
           this.setState({...this.state, icon: 'bars', menuIsShow: false});
    };

    logOut = () => {
        if(window.confirm("Are you sure?")) {
            this.props.logOut();
            this.props.history.push('/')
        }
        this.showMenu()
    };

    render() {
        return (
            <div>
                <Button basic color="teal" icon onClick={this.showMenu}>
                    <Icon name={this.state.icon}/>
                </Button>
                {this.state.menuIsShow &&
                    <div className='navbar-menu__mini'>
                        <Menu inverted vertical>
                            <Menu.Item name='home' as='a' onClick={() => this.props.history.push('/')}>
                                Home
                            </Menu.Item>
                            <Menu.Item
                                as='a'
                                name='looks'
                                onClick={this.handleItemClick}
                            />
                            <Menu.Item
                                as='a'
                                name='settings'
                                onClick={() => this.props.history.push('/settings')}
                            />
                            {this.props.data.userData.userRole.roleName === "ADMIN" &&
                                <Menu.Item
                                    as='a'
                                    name='admin'
                                    onClick={() => this.props.history.push('/admin')}
                                />
                            }
                            <Menu.Item
                                as='a'
                                className='navbar-menu__red-item'
                                name='log out'
                                color='red'
                                onClick={this.logOut}
                            />
                        </Menu>
                    </div>

                }
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

export default withRouter(connect(mapStateToProps, matchDispatchToProps)(NavMenu));