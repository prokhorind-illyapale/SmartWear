import React, { Component } from 'react';
import { Button, Header, Form, Segment, Icon } from 'semantic-ui-react';

class SignIn extends Component {


    render() {
        return (
            <Segment placeholder className='authContainer'>
                <Header as='h2' icon textAlign='center'>
                    <Icon name='sign-in' circular />
                    <Header.Content>Sign In</Header.Content>
                </Header>
                <Form>
                    <Form.Input icon='user' iconPosition='left' label='Username' placeholder='Username' />
                    <Form.Input icon='lock' iconPosition='left' label='Password' type='password' />

                    <Button content='Login' primary />
                </Form>
            </Segment>
        )
    }

}


export default SignIn