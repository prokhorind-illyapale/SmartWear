import React, { Component } from 'react';
import axios from 'axios'
import {bindActionCreators} from "redux";
import {getAllUsers} from "../../actions/getAllUsers";
import connect from "react-redux/es/connect/connect";
import { List, Icon, Segment } from 'semantic-ui-react'
import '../../styleForComponents/AuthPage.css';


const styleContainer ={
    margin: '70px 0 0 0'
}

class Admin extends Component {


    componentDidMount(){
        let url = 'http://localhost:8080/user/get/all';

        axios.get(url, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token,
            }
        })
            .then(response => {
                this.props.getAllUsers(response.data)
            })
            .catch(err => console.error(err))
    }

    render() {
          return this.props.data.allUsers.map(data => {
            return (
                <Segment compact style={styleContainer}>
                    <List>
                        <List.Item>
                            <Icon avatar name="user"/>
                            <List.Content>
                                <List.Header as='span'>{data.login}</List.Header>
                                <List.Description>
                                    City: {data.city}
                                </List.Description>
                                <List.Description>
                                    Email: {data.email}
                                </List.Description>
                            </List.Content>
                        </List.Item>
                    </List>
                </Segment>
            )
        })

    }

}

function matchDispatchToProps(dispath) {
    return bindActionCreators( { getAllUsers: getAllUsers }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.appData
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(Admin);