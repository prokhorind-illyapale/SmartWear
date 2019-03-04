import React, { Component } from 'react';
import { Button, Header, Form, Segment, Icon } from 'semantic-ui-react';

class SignIn extends Component {

    state = {
        login: '',
        password: ''
    };

    onLoginSet =(e) => {
        this.setState({...this.state, login: e.target.value})
    };

    onPassSet =(e) => {
        this.setState({...this.state, password: e.target.value})
    };

    onSubmit = (e) => {
        e.preventDefault();
        // let base64 = require('base-64');

let url = 'http://localhost:8080/user/get/customer';

let headers = new Headers();

//headers.append('Content-Type', 'text/json');
headers.set('Authorization', 'Basic ' + btoa(this.state.login + ":" +this.state.password));

fetch(url, {method:'GET',
        headers: headers,
        //credentials: 'user:passwd'
       })
.then(response => response.json())
.then(json => console.log(json));
//.done();

function parseJSON(response) {
return response.json()
}
//         let param = btoa(`${this.state.login}:${this.state.password}`);
        
//         return fetch(`http://localhost:8080/user/get/customer`, {
//             mode: 'no-cors',
//             headers: { 
//                 "Authentication": `Basic ${param}` 
//             }
//   })
//     .then(response => console.log(response.json()))
//     .then(data => console.log(data))
//     .catch(er => console.log(er));
};

    render() {
        return (
            <Segment placeholder className='authContainer'>
                <Header as='h2' icon textAlign='center'>
                    <Icon name='sign-in' circular />
                    <Header.Content>Sign In</Header.Content>
                </Header>
                <Form>
                    <Form.Input icon='user' iconPosition='left' label='Username' placeholder='Username' onChange={this.onLoginSet} />
                    <Form.Input icon='lock' iconPosition='left' label='Password' type='password' onChange={this.onPassSet}/>

                    <Button content='Login' primary onClick={this.onSubmit}/>
                </Form>
            </Segment>
        )
    }

}


export default SignIn