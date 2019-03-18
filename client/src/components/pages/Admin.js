import React, { Component } from 'react';
import axios from 'axios'
import {bindActionCreators} from "redux";
import {getAllUsers} from "../../actions/getAllUsers";
import connect from "react-redux/es/connect/connect";
import {Table, Button } from 'semantic-ui-react'
import '../../styleForComponents/AuthPage.css';
import {deleteUser} from "../../actions/deleteUser";


const styleContainer ={
    position: 'absolute',
    transform: 'translateX(-50%)',
    top: '20%',
    left: '50%',
    width: '80%'
};

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

    deleteUserByLogin = (login) => {
        if(window.confirm('Are you sure?')) {
            this.props.deleteUser(login);
            let url = 'http://localhost:8080/user/delete/';
            axios.delete(url + login, {
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(data => data)
                .catch(err => console.log(err))
        }

    };


    tableUserData() {
        return this.props.data.map((data) => {
            return (
                <Table.Body key={data.login}>
                    <Table.Row>
                        <Table.Cell>{data.login}</Table.Cell>
                        <Table.Cell>{data.city}</Table.Cell>
                        <Table.Cell>{data.email}</Table.Cell>
                        <Table.Cell>{data.sex === "M" ? "Male" : "Female"}</Table.Cell>
                        <Table.Cell>{data.userRole.roleName}</Table.Cell>
                        <Table.Cell>
                            <Button basic negative onClick={() => this.deleteUserByLogin(data.login)}>Delete</Button>
                        </Table.Cell>
                    </Table.Row>
                </Table.Body>
            )
        });
    }

    render() {
            return (
                <Table celled style={styleContainer}>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Login</Table.HeaderCell>
                            <Table.HeaderCell>City</Table.HeaderCell>
                            <Table.HeaderCell>Email</Table.HeaderCell>
                            <Table.HeaderCell>Gender</Table.HeaderCell>
                            <Table.HeaderCell>Role</Table.HeaderCell>
                            <Table.HeaderCell>Delete</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    {this.tableUserData()}
                </Table>
            )
    }

}

function matchDispatchToProps(dispath) {
    return bindActionCreators( { getAllUsers: getAllUsers, deleteUser: deleteUser }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.usersData
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(Admin);