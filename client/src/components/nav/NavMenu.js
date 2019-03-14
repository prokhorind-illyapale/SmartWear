import React, { Component } from 'react';
import { Button, Icon,  Menu } from 'semantic-ui-react';
import '../../styleForComponents/NavMenu.css';
import { connect } from 'react-redux';
import { Link } from "react-router-dom";

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

    render() {
        return (
            <div>
                <Button basic color="teal" icon onClick={this.showMenu}>
                    <Icon name={this.state.icon}/>
                </Button>
                {this.state.menuIsShow &&
                    <div className='navbar-menu__mini'>
                        <Menu inverted vertical>
                            <Link to={`/`}>
                                <Menu.Item name='home' as='span' onClick={this.handleItemClick}>
                                    Home
                                </Menu.Item>
                            </Link>
                            <Menu.Item
                                name='looks'
                                onClick={this.handleItemClick}
                            />
                            <Menu.Item
                                name='settings'
                                onClick={this.handleItemClick}
                            />
                            {this.props.data.userData.userRole.roleName === "ADMIN" &&
                                <Link to={`/admin`}>
                                    <Menu.Item
                                        as='span'
                                        name='admin'
                                        onClick={this.handleItemClick}
                                    />
                                </Link>
                            }
                            <Menu.Item
                                className='navbar-menu__red-item'
                                name='log out'
                                color='red'
                                onClick={this.handleItemClick}
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

export default connect(mapStateToProps)(NavMenu);