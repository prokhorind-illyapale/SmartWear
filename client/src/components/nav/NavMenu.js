import React, { Component } from 'react';
import { Button, Icon,  Menu } from 'semantic-ui-react';
import '../../styleForComponents/NavMenu.css';

class NavMenu  extends Component {

    state = {
        icon: 'bars',
        activeItem: 'home',
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
                            <Menu.Item name='home' active={this.state.activeItem === 'home'} onClick={this.handleItemClick}>
                                Home
                            </Menu.Item>
                            <Menu.Item
                                name='looks'
                                active={this.state.activeItem === 'messages'}
                                onClick={this.handleItemClick}
                            />
                            <Menu.Item
                                name='settings'
                                active={this.state.activeItem === 'friends'}
                                onClick={this.handleItemClick}
                            />
                            <Menu.Item
                                className='navbar-menu__red-item'
                                name='log out'
                                color='red'
                                active={this.state.activeItem === 'friends'}
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