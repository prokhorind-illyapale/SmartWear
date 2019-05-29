import React, { Component } from 'react';
import axios from 'axios';
import {bindActionCreators} from "redux";
import {getAllUsers} from "../../../actions/getAllUsers";
import connect from "react-redux/es/connect/connect";
import {Table, Button, Icon, Modal, Form} from 'semantic-ui-react';
import '../../../styleForComponents/AuthPage.css';
import {deleteUser} from "../../../actions/deleteUser";
import {updateUserByLogin} from "../../../actions/updateUserByLogin";
import {ToastContainer, toast } from 'react-toastify';


const styleContainer ={
    margin: '10px auto',
};

class Users extends Component {

    state = {
        id: '',
        open: false,
        login: '',
        email: '',
        city: '',
        sex: '',
        userRole: {roleName: ''},
        open_pass: false,
        password: '',
        repeatPassword: '',
        open_confirm: false
    };

    showEditModal = ({login, email, city, sex, userRole}) => {
        this.setState({...this.state, open: true, login, email, city, sex, userRole, id: login});
    };

    closeEditModal = () =>
        this.setState({ open: false });

    showPassModal = ({login}) => {
        this.setState({...this.state, open_pass: true, id: login})
    };

    closePassModal = () =>
        this.setState({ open_pass: false });

    editField =({target}) => {
       this.setState({...this.state, [target.name]: target.value })
    };

    editRoleName = (e) => {
        this.setState(Object.assign(this.state.userRole,{roleName: e.target.value}));
    };


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

    updatePassword = () => {
        let url = 'http://localhost:8080/admin/change-user-password',
            password = this.state.password,
            repeat_pass = this.state.repeatPassword,
            body = JSON.stringify({
                login: this.state.id,
                password: password
            });

        if(repeat_pass === password) {
            axios.post(url, body, {
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    if(response.status === 200) {
                        this.closePassModal();
                        toast.success('Success', {
                            position: toast.POSITION.TOP_CENTER
                        });
                    }
                })
                .catch(err => console.log(err));
        } else {
            toast.error('Password does not match', {
                position: toast.POSITION.TOP_CENTER
            });
        }

        this.setState({...this.state, password: '', repeatPassword: '', id: ''})
    };

    updateUserByLogin = (login) => {
        let url = 'http://localhost:8080/user/update/',
            fields = {
                login: this.state.login,
                email: this.state.email,
                city: this.state.city,
                sex: this.state.sex,
                userRole: {roleName: this.state.userRole.roleName}
            },
            body = JSON.stringify({
                login: this.state.login,
                email: this.state.email,
                city: this.state.city,
                sex: this.state.sex,
                userRole: {roleName: this.state.userRole.roleName}
            });

        axios.put(url + login, body, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    toast.success('Success', {
                        position: toast.POSITION.TOP_CENTER
                    });
                    this.props.updateUserByLogin(login, fields);
                    this.closeEditModal();
                }
            })
            .catch(err => console.log(err));
    };



    tableUserData() {
        let myData = this.props.myData.userData;
        return this.props.data.map((data) => {
            return (
                <Table.Body key={data.login}>
                    <Table.Row active={data.login === myData.login}>
                        <Table.Cell>{data.login}</Table.Cell>
                        <Table.Cell>{data.city}</Table.Cell>
                        <Table.Cell>{data.email}</Table.Cell>
                        <Table.Cell textAlign='center'>{data.sex}</Table.Cell>
                        <Table.Cell textAlign='center'>{data.userRole.roleName}</Table.Cell>
                        <Table.Cell textAlign='center'>
                            <Button basic icon negative onClick={() => this.deleteUserByLogin(data.login)}>
                                <Icon name='cancel'/>
                            </Button>
                        </Table.Cell>
                        <Table.Cell textAlign='center'>
                            <Button basic icon color='blue' onClick={() => this.showEditModal(data)}>
                                <Icon name='edit'/>
                            </Button>
                            <Modal size='small' open={this.state.open} onClose={this.closeEditModal} closeIcon>
                                <Modal.Header>Edit {this.state.id} account</Modal.Header>
                                <Modal.Content>
                                    <Form>
                                        <Form.Field>
                                            <label>Login</label>
                                            <input name='login' defaultValue={this.state.login} onChange={this.editField}/>
                                        </Form.Field>
                                        <Form.Field>
                                            <label>Email</label>
                                            <input name='email' defaultValue={this.state.email} onChange={this.editField}/>
                                        </Form.Field>
                                        <Form.Field>
                                            <label>City</label>
                                            <input name='city' defaultValue={this.state.city} onChange={this.editField}/>
                                        </Form.Field>
                                        <Form.Field>
                                            <label>Gender</label>
                                            <input name='sex' defaultValue={this.state.sex} onChange={this.editField}/>
                                        </Form.Field>
                                        <Form.Field>
                                            <label>Role</label>
                                            <input defaultValue={this.state.userRole.roleName} onChange={this.editRoleName}/>
                                        </Form.Field>
                                    </Form>
                                </Modal.Content>
                                <Modal.Actions>
                                    <Button type='submit' onClick={() => this.updateUserByLogin(this.state.id)}>Submit</Button>
                                </Modal.Actions>
                            </Modal>
                        </Table.Cell>
                        <Table.Cell textAlign='center'>
                            <Button basic icon onClick={() => this.showPassModal(data)}>
                                <Icon name='key'/>
                            </Button>
                            <Modal size='small' open={this.state.open_pass} onClose={this.closePassModal} closeIcon>
                            <Modal.Header>Edit {this.state.id} password</Modal.Header>
                            <Modal.Content>
                                <Form>
                                    <Form.Field>
                                        <label>New Password</label>
                                        <input type='password' name='password' defaultValue={this.state.password} onChange={this.editField}/>
                                    </Form.Field>
                                    <Form.Field>
                                        <label>Repeat Password</label>
                                        <input type='password' name='repeatPassword' defaultValue={this.state.repeatPassword} onChange={this.editField}/>
                                    </Form.Field>
                                </Form>
                            </Modal.Content>
                            <Modal.Actions>
                                <Button type='submit' onClick={() => this.updatePassword(this.state.id)}>Submit</Button>
                            </Modal.Actions>
                        </Modal>
                        </Table.Cell>
                    </Table.Row>
                </Table.Body>
            )
        });
    }

    render() {
            return (
                <div>
                    <ToastContainer/>
                    <Table celled style={styleContainer}>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>Login</Table.HeaderCell>
                                <Table.HeaderCell>City</Table.HeaderCell>
                                <Table.HeaderCell>Email</Table.HeaderCell>
                                <Table.HeaderCell textAlign='center'>Gender</Table.HeaderCell>
                                <Table.HeaderCell textAlign='center'>Role</Table.HeaderCell>
                                <Table.HeaderCell textAlign='center'>Delete</Table.HeaderCell>
                                <Table.HeaderCell textAlign='center'>Edit</Table.HeaderCell>
                                <Table.HeaderCell textAlign='center'>Password</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>
                        {this.tableUserData()}
                    </Table>
                </div>

            )
    }

}

function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            getAllUsers: getAllUsers,
            deleteUser: deleteUser,
            updateUserByLogin: updateUserByLogin
        }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.usersData,
        myData: state.appData
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(Users);