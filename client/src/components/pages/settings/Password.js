import React, { Component } from 'react';
import {Button, Form} from 'semantic-ui-react';
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";
import connect from "react-redux/es/connect/connect";
import {bindActionCreators} from "redux";
import {setToken} from "../../../actions/setToken";



class Password extends Component {

    state = {
        old_pass: '',
        new_pass: '',
        rep_new_pass: ''
    };

    setPasswordField = ({target}) => {
        this.setState({...this.state, [target.name]: target.value })
    };

    changePassword = (login) => {
        if(this.state.rep_new_pass === this.state.new_pass) {
            let url = 'http://localhost:8080/user/change-user-password',
                body = JSON.stringify({
                    login: login,
                    oldPassword: this.state.old_pass,
                    newPassword: this.state.new_pass
                });

            axios.post(url, body, {
                headers: {
                    'Authorization': "Basic " + window.localStorage.token,
                    'Content-type' : 'application/json',
                }
            })
                .then(data => {
                    if(data.status === 200) {
                        this.props.setToken(btoa(login + ':' + this.state.new_pass));
                        window.localStorage.token = btoa(login + ':' + this.state.new_pass);
                        toast.success('Password changed', {
                            position: toast.POSITION.TOP_CENTER
                        });
                    }
                })
                .catch(err => console.error(err))

        } else {
            toast.error("Password does not match", {
                position: toast.POSITION.TOP_CENTER
            });
        }
    };

    render() {
        let data = this.props.myData.userData;
        return (
            <div>
                <ToastContainer/>
                <Form>
                    <Form.Input id="old_pass" name="old_pass" icon='lock' iconPosition='left' label='Old Password' type='password' onChange={this.setPasswordField}/>
                    <Form.Input id="new_pass" name="new_pass"  icon='lock' iconPosition='left' label='New Password' type='password' onChange={this.setPasswordField}/>
                    <Form.Input id="rep_new_pass" name='rep_new_pass'  icon='lock' iconPosition='left' label='Repeat your password' type='password' onChange={this.setPasswordField}/>
                    <Button content='Save'  primary onClick={() => this.changePassword(data.login)}/>
                </Form>
            </div>
        )
    }
}
function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            setToken: setToken
        }, dispath);
}

function mapStateToProps(state) {
    return {
        myData: state.appData
    }
}


export default connect(mapStateToProps, matchDispatchToProps)(Password) ;