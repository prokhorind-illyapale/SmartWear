import React, { Component } from 'react';
import {Segment, Header, Icon, Menu, Grid} from 'semantic-ui-react';
import connect from "react-redux/es/connect/connect";
import {Link, Route} from "react-router-dom";
import {bindActionCreators} from "redux";
import {deleteUser} from "../../../actions/deleteUser";
import Profile from "./Profile";
import Password from "./Password";
import Account from "./Account";


const container = {
    width: '80%',
    margin: '100px auto'
};


class Settings extends Component {

    state = {
        activeItem: 'Profile'
    };

    handleItemClick = (e, { name }) => this.setState({ activeItem: name });

    render() {
        const { activeItem } = this.state;

        return (
            <Segment style={container}>
                <Grid>
                    <Grid.Row>
                        <Grid.Column width={4}>
                            <Header as='h4'>
                                <Icon name='settings' />
                                User Settings
                            </Header>
                            <Menu pointing secondary vertical>
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
                        <Grid.Column width={10}>
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
function matchDispatchToProps(dispath) {
    return bindActionCreators( { deleteUser: deleteUser }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.appData
    };

}

export default connect(mapStateToProps, matchDispatchToProps)(Settings) ;