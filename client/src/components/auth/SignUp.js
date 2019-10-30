import React, { Component } from 'react';
import { Button, Header, Form, Segment, Icon, Progress } from 'semantic-ui-react';
import {ToastContainer, toast } from 'react-toastify';
import axios from 'axios';
import {bindActionCreators} from "redux";
import { connect } from 'react-redux';
import {setToken} from "../../actions/setToken";


const gender = [
    { key: 'm', text: 'Male', value: 'M' },
    { key: 'f', text: 'Female', value: 'F' },
];

class SignUp extends Component {

    state = {
        login: '',
        email: '',
        city: '',
        gender: '',
        password: '',
        passwordRepeat: '',
        progress: false,
        percent: 10,
        color: 'grey'
    };

    onLoginSet = (e) => {
        this.setState({...this.state, login: e.target.value})
    };

    onEmailSet = (e) => {
        this.setState({...this.state, email: e.target.value})
    };

    onCitySet = (e) => {
        this.setState({...this.state, city: e.target.value})
    };

    onGenderSet = (e, {value}) => {
        this.setState({...this.state, gender: value})
    };

    onPassSet = (e) => {
        this.setState({...this.state, password: e.target.value, progress: true});

        let length = e.target.value.length;

        if(length === 3) {
            this.setState({percent: 30, color: 'grey'});
        } else if (length === 6) {
            this.setState({percent: 60, color: 'grey'});
        } else if (length === 8) {
            this.setState({percent: 80, color: 'yellow'});
        } else if (length === 10) {
            this.setState({percent: 100, color: 'green'});
        }  else if (!length ) {
            this.setState({progress: false, color: 'grey', percent: 10});
        }
    };

    onPassRepeatSet = (e) => {
        this.setState({...this.state, passwordRepeat: e.target.value})
    };

    validateEmail = () => {
        // eslint-disable-next-line
        const emailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        let email = this.state.email;

        if(email.match(emailformat)) {
            return true;
        } else {
            toast.error("You have entered an invalid email address", {
                position: toast.POSITION.TOP_CENTER
            });
            return false;
        }
    };

    validateForm = () => {
        if(this.state.login !== ''
            && this.state.city !== ''
            && this.state.email !== ''
            && this.state.gender !== ''
            && this.state.password !== ''
            && this.state.passwordRepeat !== '')
        {
            return true;
        } else {
                toast.warn("All fields are required", {
                    position: toast.POSITION.TOP_CENTER
                });

            return false;
        }
    };

    checkPass = () => {
        if (this.state.passwordRepeat === this.state.password) {
            return true;
        } else {
            toast.error("Password does not match", {
                position: toast.POSITION.TOP_CENTER
            });
            return false;
        }
    };


    onSubmit = () => {
        if(this.validateForm() && this.checkPass() && this.validateEmail()) {
            let url = 'http://localhost:8080/registry',
                login_input = document.getElementById('login_input'),
                email_input = document.getElementById('email_input'),
                city_input = document.getElementById('city_input'),
                gender_input = document.getElementById('gender_input'),
                pass_input = document.getElementById('pass_input'),
                pass_rep_input = document.getElementById('pass_rep_input'),
                auth_data = btoa(this.state.login + ":" + this.state.password),
                body = JSON.stringify({
                    login: this.state.login,
                    password: this.state.password,
                    email: this.state.email,
                    city: this.state.city,
                    sex: this.state.gender,
                });

            axios.post(url, body, {
                headers :{
                    'Content-type' : 'application/json'
                }
            })
                .then(data => {
                    if(data) {
                        this.props.setToken(auth_data);
                        this.props.history.push('/')
                    }
                })
                .catch(function (error) {
                    console.log('Request failed', error);
                });

            this.setState({...this.state, login: '', password: '', email: '', city: '', sex: '', passwordRepeat: ''});
            login_input.value = '';
            email_input.value = '';
            city_input.value = '';
            gender_input.value = '';
            pass_input.value = '';
            pass_rep_input.value = '';
        }
    };

    componentDidMount() {
        if(window.innerHeight < 900) {
            let regContainer = this.refs.regContainer;
            regContainer.style = 'overflow-y: auto; height: 300px';
        }
    };

    render() {
        const value  = this.state.gender;
        return (
            <div>
                <ToastContainer />
                <Segment placeholder className='auth-container'>
                    <Header as='h2' icon textAlign='center'>
                        <Icon name='signup' circular />
                        <Header.Content>Register</Header.Content>
                    </Header>
                    <div ref="regContainer">
                        <Form>
                            <Form.Input id="login_input" onChange={this.onLoginSet} icon='user' iconPosition='left' label='Login' placeholder='Login' />
                            <Form.Input id="email_input" onChange={this.onEmailSet} icon='envelope' iconPosition='left' label='Email' placeholder='Email' />
                            <Form.Input id="city_input" onChange={this.onCitySet} icon='location arrow' iconPosition='left' label='Location' placeholder='Your city' />
                            <Form.Select id="gender_input" onChange={this.onGenderSet} fluid label='Gender' options={gender} placeholder='Gender' value={value}/>
                            <Form.Input id="pass_input" onChange={this.onPassSet} icon='lock' iconPosition='left' label='Password' type='password' />
                            {this.state.progress && <Form.Field><Progress percent={this.state.percent} size='small' progress color={this.state.color}/></Form.Field>}
                            <Form.Input id="pass_rep_input" onChange={this.onPassRepeatSet} icon='lock' iconPosition='left' label='Repeat your password' type='password' />

                            <Button content='Register' onClick={this.onSubmit} primary />
                        </Form>
                    </div>
                </Segment>
            </div>

        )
    }

}

function matchDispatchToProps(dispath) {
    return bindActionCreators( { setToken: setToken }, dispath);
}

export default connect(null, matchDispatchToProps)(SignUp)