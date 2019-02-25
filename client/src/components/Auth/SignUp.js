import React, { Component } from 'react';
import { Button, Header, Form, Segment, Icon } from 'semantic-ui-react';

const gender = [
    { key: 'm', text: 'Male', value: 'M' },
    { key: 'f', text: 'Female', value: 'F' },
];

class SignUp extends Component {

    componentDidMount() {
        if(window.innerHeight < 900) {
            let regContainer = this.refs.regContainer;
            regContainer.style = 'overflow-y: auto; height: 300px';
        }
    };

    render() {
        return (
            <Segment placeholder className='authContainer'>
                <Header as='h2' icon textAlign='center'>
                    <Icon name='signup' circular />
                    <Header.Content>Register</Header.Content>
                </Header>
                <div ref="regContainer">
                    <Form>
                        <Form.Input icon='user' iconPosition='left' label='Username' placeholder='Username' />
                        <Form.Input icon='envelope' iconPosition='left' label='Email' placeholder='Email' />
                        <Form.Input icon='location arrow' iconPosition='left' label='Location' placeholder='Your city' />
                        <Form.Select fluid label='Gender' options={gender} placeholder='Gender' />
                        <Form.Input icon='lock' iconPosition='left' label='Password' type='password' />
                        <Form.Input icon='lock' iconPosition='left' label='Repeat your password' type='password' />

                        <Button content='Register' primary />
                    </Form>
                </div>
            </Segment>
        )
    }

}


export default SignUp