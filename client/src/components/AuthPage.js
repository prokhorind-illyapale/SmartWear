import React, { Component } from 'react';
import { Button, Divider, Form, Grid, Segment } from 'semantic-ui-react';
import '../styleForComponents/AuthPage.css';

class AuthPage extends Component {

    componentDidMount() {
        if(window.innerWidth <= 767) {
            let devider = this.refs.deviderAuth;
            devider.style = 'display: none';
        }
    };

    render() {
        return (
            <div className='authBody'>
                <Segment placeholder className='authContainer'>
                    <Grid columns={2} relaxed='very' stackable>
                        <Grid.Column>
                            <Form>
                                <Form.Input icon='user' iconPosition='left' label='Username' placeholder='Username' />
                                <Form.Input icon='lock' iconPosition='left' label='Password' type='password' />

                                <Button content='Login' primary />
                            </Form>
                        </Grid.Column>

                        <Grid.Column verticalAlign='middle'>
                            <Button content='Register' icon='signup' size='big' />
                        </Grid.Column>
                    </Grid>
                    <div ref='deviderAuth'>
                        <Divider vertical>Or</Divider>
                    </div>


                </Segment>
            </div>
        )
    }

}


export default AuthPage