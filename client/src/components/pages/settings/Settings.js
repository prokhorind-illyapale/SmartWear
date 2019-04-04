import React, { Component } from 'react';
import {Segment, Header, Icon, Menu, Grid} from 'semantic-ui-react';
import {Link, Route} from "react-router-dom";
import Profile from "./Profile";
import Password from "./Password";
import Account from "./Account";


const container = {
    width: '80%',
    margin: '10% auto',
};


class Settings extends Component {

    state = {
        activeItem: 'Profile',
        menuWidth: 4,
        settingsWidth: 10,
        vertical: true,
        horizontal: "false"
    };

    handleItemClick = (e, { name }) => this.setState({ activeItem: name });

    setActiveItem = () => {
        let href =  this.props.location.pathname,
            nameLow = href.substr(10),
            nameUp = nameLow.charAt(0).toUpperCase() + nameLow.slice(1);


        if(nameUp !== '') {
            this.setState({ activeItem: nameUp });
        } else {
            this.setState({ activeItem: 'Profile' });
        }
    };

    updateContainer() {
        if(window.innerWidth < 1200) {
            this.setState({menuWidth: 16, settingsWidth: 16, vertical: false, horizontal: "true"})
        } else if(window.innerWidth >= 1200) {
            this.setState({menuWidth: 4, settingsWidth: 10, vertical: true, horizontal: "false"})
        }
    }

    componentDidMount() {
        this.setActiveItem();
        this.updateContainer();
        window.addEventListener('resize',() => this.updateContainer())
    }


    componentWillUnmount() {
        window.removeEventListener('resize',() => this.updateContainer())
    }

    render() {
        const { activeItem } = this.state;

        return (
            <Segment style={container}>
                <Grid>
                    <Grid.Row>
                        <Grid.Column width={this.state.menuWidth}>
                            <Header as='h4'>
                                <Icon name='settings' />
                                User Settings
                            </Header>
                            <Menu pointing secondary vertical={this.state.vertical} horizontal={this.state.horizontal}>
                                <Link to={`/settings`}>
                                    <Menu.Item
                                        as='span'
                                        name='Profile'
                                        active={activeItem === 'Profile'}
                                        onClick={this.handleItemClick}/>
                                </Link>
                                <Link to={`/settings/password`}>
                                    <Menu.Item
                                        as='span'
                                        name='Password'
                                        active={activeItem === 'Password'}
                                        onClick={this.handleItemClick}
                                    />
                                </Link>

                                <Link to={`/settings/account`}>
                                    <Menu.Item
                                        as='span'
                                        name='Account'
                                        active={activeItem === 'Account'}
                                        onClick={this.handleItemClick}
                                    />
                                </Link>

                                <Link to={`/settings/looks`}>
                                    <Menu.Item
                                        as='span'
                                        name='Looks'
                                        active={activeItem === 'Looks'}
                                        onClick={this.handleItemClick}
                                    />
                                </Link>

                                <Link to={`/settings/emails`}>
                                    <Menu.Item
                                        as='span'
                                        name='Emails'
                                        active={activeItem === 'Emails'}
                                        onClick={this.handleItemClick}
                                    />
                                </Link>

                            </Menu>
                        </Grid.Column>
                        <Grid.Column width={this.state.settingsWidth}>
                            <Route exact path='/settings' component={Profile}/>
                            <Route path='/settings/password' component={Password}/>
                            <Route path='/settings/account' component={Account}/>
                        </Grid.Column>
                    </Grid.Row>
                </Grid>

            </Segment>
        )
    }
}

export default Settings ;