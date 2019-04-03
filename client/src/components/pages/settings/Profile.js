import React, { Component } from 'react';
import {Button, Form} from 'semantic-ui-react';
import connect from "react-redux/es/connect/connect";
import axios from "axios";
import {bindActionCreators} from "redux";
import {deleteUser} from "../../../actions/deleteUser";

const gender = [
    { key: 'm', text: 'Male', value: 'M' },
    { key: 'f', text: 'Female', value: 'F' },
];



class Profile extends Component {

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
            <Form>
                <Form.Input id="login_input"  icon='user' iconPosition='left' label='Login' name='login' placeholder='Login' onChange={this.editField} defaultValue={this.state.login}/>
                <Form.Input id="email_input"  icon='envelope' iconPosition='left' label='Email' name='email' placeholder='Email' onChange={this.editField} defaultValue={this.state.email}/>
                <Form.Input id="city_input"  icon='location arrow' iconPosition='left' label='Location' name='city' placeholder='Your city' onChange={this.editField} defaultValue={this.state.city}/>
                <Form.Select id="gender_input"  fluid label='Gender' options={gender} placeholder='Gender' name='sex' onChange={this.editField} defaultValue={this.state.sex}/>

                <Button content='Save'  primary />
            </Form>
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

export default connect(mapStateToProps, matchDispatchToProps)(Profile) ;