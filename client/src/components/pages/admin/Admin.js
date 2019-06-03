import React, { Component } from 'react';
import {Segment, Menu} from 'semantic-ui-react';
import {Link, Route} from "react-router-dom";
import Users from "./Users";
import clothTypes from './clothTypes';
import lookTypes from './lookTypes'

const container = {
    width: '80%',
    margin: '10% auto',
};

class Admin extends Component { 
    
    state = {
        activeItem: 'users',
    };

    handleItemClick = (e, { name }) => this.setState({ activeItem: name });

    setActiveItem = () => {
        let href =  this.props.location.pathname,
            name = href.substr(href.lastIndexOf('/') + 1);
        
        if(name !== 'admin') {
            this.setState({ activeItem: name });
        } else {
            this.setState({ activeItem: 'users' });
        }
    };
    
    componentDidMount() {
        this.setActiveItem()
    }
    
    render() {
        const { activeItem } = this.state;
        
        return(
            <Segment style={container}>
                <Menu>
                    <Link to={`/admin`}>
                        <Menu.Item
                            as='span'
                            name='users'
                            active={activeItem === 'users'}
                            onClick={this.handleItemClick}
                        >
                            Users
                        </Menu.Item>
                    </Link>
                    <Link to={`/admin/clothtypes`}>
                        <Menu.Item
                            as='span'
                            name='clothtypes'
                            active={activeItem === 'clothtypes'}
                            onClick={this.handleItemClick}
                        >
                            Cloth Types
                        </Menu.Item>
                    </Link>
                    <Link to={`/admin/looktypes`}>
                        <Menu.Item
                            as='span'
                            name='looktypes'
                            active={activeItem === 'looktypes'}
                            onClick={this.handleItemClick}
                        >
                            Look Types
                        </Menu.Item>
                    </Link>
                </Menu>
                <Route exact path='/admin' component={Users}/>
                <Route path='/admin/clothtypes' component={clothTypes}/>
                <Route path='/admin/looktypes' component={lookTypes}/>
            </Segment>
        )
    }
}

export default Admin;