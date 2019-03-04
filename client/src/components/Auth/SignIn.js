import React, { Component } from 'react';
import { Button, Header, Form, Segment, Icon } from 'semantic-ui-react';

class SignIn extends Component {

    state = {
        login: '',
        password: '',
        isLoading: false,
        auth_data: []
    };

    onLoginSet =(e) => {
        this.setState({...this.state, login: e.target.value})
    };

    onPassSet =(e) => {
        this.setState({...this.state, password: e.target.value})
    };

    onSubmit = (e) => {

        e.preventDefault();

        let url = 'http://localhost:8080/user/get/customer',
        headers = new Headers();

        headers.set('Authorization', 'Basic ' + btoa(this.state.login + ":" + this.state.password));

        fetch(url, {
            method:'GET',
            headers: headers,
       })
        .then(response => response.json())
        .then(data => this.setState({...this.state, auth_data : data}))
        .catch(status => alert(status));


        this.setState({...this.state, isLoading: false, login: '', password: ''});
    };

    render() {
        return (
            <Segment placeholder className='auth-container'>
                <Header as='h2' icon textAlign='center'>
                    <Icon name='sign-in' circular />
                    <Header.Content>Sign In</Header.Content>
                </Header>
                <Form>
                    <Form.Input icon='user' iconPosition='left' label='Username' placeholder='Username' onChange={this.onLoginSet} />
                    <Form.Input icon='lock' iconPosition='left' label='Password' type='password' onChange={this.onPassSet}/>

                    <Button loading={this.state.isLoading} content='Login' primary onClick={this.onSubmit}/>
                </Form>
            </Segment>
        )
    }

}


export default SignIn