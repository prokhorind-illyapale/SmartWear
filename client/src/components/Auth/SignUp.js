import React, { Component } from 'react';
import { Button, Header, Form, Segment, Icon } from 'semantic-ui-react';

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
        passwordRepeat: ''
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
        this.setState({...this.state, password: e.target.value})
    };

    onPassRepeatSet = (e) => {
        this.setState({...this.state, passwordRepeat: e.target.value})
    };

    valideteEmail = () => {
        // eslint-disable-next-line
        const emailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        let email = this.state.email;

        if(email.match(emailformat)) {
            return true;
        } else {
            alert("You have entered an invalid email address!");
            return false;
        }
    };

    valideteForm = () => {
        if(this.state.login !== '' && this.state.city !== '' && this.state.email !== '' && this.state.gender !== '' && this.state.password !== '' && this.state.passwordRepeat !== '') {
            return true;
        } else {
            alert("All fields is required");
            return false;
        }
    };

    checkPass = () => {
        if (this.state.passwordRepeat === this.state.password) {
            return true;
        } else {
            alert("Password does not match");
            return false;
        }
    };


    onSubmit = (e) => {
        e.preventDefault();
        if(this.valideteForm() && this.checkPass() && this.valideteEmail()) {

            let url = 'http://localhost:8080/registry';
            fetch(url, {
                method:'POST',
                headers :{
                    'Content-type' : 'application/json'
                },
                body: JSON.stringify({
                    login: this.state.login,
                    password: this.state.password,
                    email: this.state.email,
                    city: this.state.city,
                    sex: this.state.gender,
                })
            })
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
            <Segment placeholder className='auth-container'>
                <Header as='h2' icon textAlign='center'>
                    <Icon name='signup' circular />
                    <Header.Content>Register</Header.Content>
                </Header>
                <div ref="regContainer">
                    <Form>
                        <Form.Input onChange={this.onLoginSet} icon='user' iconPosition='left' label='Login' placeholder='Login' />
                        <Form.Input onChange={this.onEmailSet} icon='envelope' iconPosition='left' label='Email' placeholder='Email' />
                        <Form.Input onChange={this.onCitySet} icon='location arrow' iconPosition='left' label='Location' placeholder='Your city' />
                        <Form.Select onChange={this.onGenderSet} fluid label='Gender' options={gender} placeholder='Gender' value={value}/>
                        <Form.Input onChange={this.onPassSet} icon='lock' iconPosition='left' label='Password' type='password' />
                        <Form.Input onChange={this.onPassRepeatSet} icon='lock' iconPosition='left' label='Repeat your password' type='password' />

                        <Button content='Register' onClick={this.onSubmit} primary />
                    </Form>
                </div>
            </Segment>
        )
    }

}


export default SignUp