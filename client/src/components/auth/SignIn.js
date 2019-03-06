import React, { Component } from 'react';
import { Button, Header, Form, Segment, Icon } from 'semantic-ui-react';
import {ToastContainer, toast } from 'react-toastify';
import {bindActionCreators} from "redux";
import { connect } from 'react-redux';
import { changeLoggedIn } from '../../actions/changeLoggedIn';

class SignIn extends Component {

    state = {
        login: '',
        password: '',
        // isLoading: false,
        // auth_data: [],
    };

    onLoginSet =(e) => {
        this.setState({...this.state, login: e.target.value})
    };

    onPassSet =(e) => {
        this.setState({...this.state, password: e.target.value})
    };

    validateForm = () => {
        if(this.state.login !== '' && this.state.password !== '')
        {
            return true;
        } else {
            toast.warn("All fields are required", {
                position: toast.POSITION.TOP_CENTER
            });

            return false;
        }
    };

    onSubmit = () => {
        if(this.validateForm()) {
            let url = 'http://localhost:8080/user/get/me',
                headers = new Headers(),
                login_input = document.getElementById('login_input'),
                password_input = document.getElementById('password_input');

            headers.set('Authorization', 'Basic ' + btoa(this.state.login + ":" + this.state.password));

            fetch(url, {
                method:'GET',
                headers: headers,
            })
                .then(response => response.json())
                .then(data => {
                    this.props.changeLoggedIn(data)
                })
                .catch(err => toast.error(err.message , {
                    position: toast.POSITION.TOP_CENTER
                }));


            this.setState({...this.state, isLoading: false, login: '', password: ''});
            password_input.value = '';
            login_input.value = '';

        }


    };

    render() {
        return (
            <div>
                <ToastContainer />
                <Segment placeholder className='auth-container'>
                    <Header as='h2' icon textAlign='center'>
                        <Icon name='sign-in' circular />
                        <Header.Content>Sign In</Header.Content>
                    </Header>
                    <Form ref="auth_form">
                        <Form.Input id="login_input" icon='user' iconPosition='left' label='Username' placeholder='Username' onChange={this.onLoginSet} />
                        <Form.Input id="password_input" icon='lock' iconPosition='left' label='Password' type='password' onChange={this.onPassSet}/>

                        <Button loading={this.state.isLoading} content='Login' primary onClick={this.onSubmit}/>
                    </Form>
                </Segment>
            </div>

        )
    }

}

function matchDispatchToProps(dispath) {
    return bindActionCreators({ changeLoggedIn: changeLoggedIn }, dispath);
}


export default connect(null, matchDispatchToProps)(SignIn)
