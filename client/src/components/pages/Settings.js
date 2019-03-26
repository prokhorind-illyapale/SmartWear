import React, { Component } from 'react';
import {Segment, Header, Icon, Button, Form, Divider, Message} from 'semantic-ui-react';
import connect from "react-redux/es/connect/connect";
import axios from "axios";
import {bindActionCreators} from "redux";
import {deleteUser} from "../../actions/deleteUser";

const gender = [
    { key: 'm', text: 'Male', value: 'M' },
    { key: 'f', text: 'Female', value: 'F' },
];

const container = {
    width: '60%',
    margin: '50px auto'
};


class Settings extends Component {

    state = {

    };

    editField =({target}) => {
        this.setState({...this.state, [target.name]: target.value })
    };

    componentDidMount() {
        if(typeof window.localStorage.token !== 'undefined') {
            let url_user = 'http://localhost:8080/user/get/',
                str = atob(window.localStorage.token),
                login = str.substring(0 ,str.indexOf(':'));

            axios.get(url_user + login, {
                headers: {
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    this.setState(response.data);
                })
                .catch(err => console.log(err));

        }
    }

    render() {

        return (
            <Segment style={container}>
                <Header as='h2' icon textAlign='center'>
                    <Icon name='cogs' circular />
                    <Header.Content>Settings</Header.Content>
                </Header>
                <Form>
                    <Form.Input id="login_input"  icon='user' iconPosition='left' label='Login' name='login' placeholder='Login' onChange={this.editField} defaultValue={this.state.login}/>
                    <Form.Input id="email_input"  icon='envelope' iconPosition='left' label='Email' name='email' placeholder='Email' onChange={this.editField} defaultValue={this.state.email}/>
                    <Form.Input id="city_input"  icon='location arrow' iconPosition='left' label='Location' name='city' placeholder='Your city' onChange={this.editField} defaultValue={this.state.city}/>
                    <Form.Select id="gender_input"  fluid label='Gender' options={gender} placeholder='Gender' name='sex' onChange={this.editField} defaultValue={this.state.sex}/>
                    <Form.Input id="pass_input"  icon='lock' iconPosition='left' label='Password' type='password'/>
                    <Form.Input id="pass_rep_input"  icon='lock' iconPosition='left' label='Repeat your password' type='password'/>

                    <Button content='Save'  primary />
                </Form>
                <Divider horizontal>
                    <Header as='h4' color='red'>
                        Danger Zone
                    </Header>
                </Divider>
                <Message negative icon>
                    <Icon name='warning sign'/>
                    <Message.Header>Attention!</Message.Header>
                </Message>
                <Button basic negative>Delete my account</Button>
            </Segment>
        )
    }
}
function matchDispatchToProps(dispath) {
    return bindActionCreators( { deleteUser: deleteUser }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.appData
    };

}

export default connect(mapStateToProps, matchDispatchToProps)(Settings) ;