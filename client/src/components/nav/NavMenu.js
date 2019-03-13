import React, { Component } from 'react';
import { Button, Icon,  Menu } from 'semantic-ui-react';
import '../../styleForComponents/NavMenu.css';

class NavMenu  extends Component {

    state = {
        icon: 'bars',
        menuIsShow: false,
    };

    handleItemClick = (e, { name }) => this.setState({ activeItem: name });

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
                            <Menu.Item name='home' onClick={this.handleItemClick}>
                                Home
                            </Menu.Item>
                            <Menu.Item
                                name='looks'
                                onClick={this.handleItemClick}
                            />
                            <Menu.Item
                                name='settings'
                                onClick={this.handleItemClick}
                            />
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

export default NavMenu;