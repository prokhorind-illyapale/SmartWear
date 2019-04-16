import React, { Component } from 'react';
import {Button, Form, Radio} from 'semantic-ui-react';
import connect from "react-redux/es/connect/connect";
import axios from "axios";
import {bindActionCreators} from "redux";
import {setToken} from "../../../actions/setToken";
import {updateUserProfile} from "../../../actions/updateUserProfile";
import {toast, ToastContainer} from "react-toastify";


class Profile extends Component {

    state = {
        id: ''
    };


    editField =({target}) => {
        this.setState({...this.state, [target.name]: target.value })
    };

    editRadio = (e, { value }) => this.setState({sex: value});

    checkForm() {
        if(this.state.login !== ""
            && this.state.email !== ""
            && this.state.city !== ""
            && this.state.sex !== ""
        ) {
           return true ;
        } else {
            toast.warn("All fields are required", {
                position: toast.POSITION.TOP_CENTER
            });
            return false;
        }
    }

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
                    this.setState({...this.state, id: response.data.login});
                })
                .catch(err => console.log(err));


        }
    }

    updateUserProfile = () => {
        if(this.checkForm()) {
            let url = 'http://localhost:8080/user/update/',
                login = this.state.id,
                fields = {
                    login: this.state.login,
                    email: this.state.email,
                    sex: this.state.sex,
                    city: this.state.city,
                    userRole: {roleName: this.state.userRole.roleName}
                },
                body = JSON.stringify({
                    login: this.state.login,
                    email: this.state.email,
                    sex: this.state.sex,
                    city: this.state.city,
                    userRole: {roleName: this.state.userRole.roleName}
                });

            axios.put(url + login, body, {
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    if (response.status === 200) {
                        this.props.updateUserProfile(fields);
                        toast.success('Profile updated', {
                            position: toast.POSITION.TOP_CENTER
                        });
                    }
                    if (login !== this.state.login) {
                        let token = atob(window.localStorage.token),
                            pass = token.substr(token.indexOf(":") + 1);

                        window.localStorage.token = btoa(this.state.login + ":" + pass);
                        this.props.setToken(btoa(this.state.login + ":" + pass));
                    }
                })
                .catch(err => console.error(err))
        }
    };

    render() {

        return (
            <div>
                <ToastContainer/>
                <Form>
                    <Form.Input id="login_input"  icon='user' iconPosition='left' label='Login' name='login' placeholder='Login' onChange={this.editField} defaultValue={this.state.login}/>
                    <Form.Input id="email_input"  icon='envelope' iconPosition='left' label='Email' name='email' placeholder='Email' onChange={this.editField} defaultValue={this.state.email}/>
                    <Form.Input id="city_input"  icon='location arrow' iconPosition='left' label='Location' name='city' placeholder='Your city' onChange={this.editField} defaultValue={this.state.city}/>
                    <Form.Field>
                      <b>Gender</b>
                    </Form.Field>
                    <Form.Field>
                        <Radio
                            label='Male'
                            name='sex'
                            value="M"
                            checked={this.state.sex === 'M'}
                            onChange={this.editRadio}
                        />
                    </Form.Field>
                    <Form.Field>
                        <Radio
                            label='Female'
                            name='sex'
                            value="F"
                            checked={this.state.sex === 'F'}
                            onChange={this.editRadio}
                        />
                    </Form.Field>
                    <Button content='Save'  primary onClick={this.updateUserProfile}/>
                </Form>
            </div>

        )
    }
}
function matchDispatchToProps(dispath) {
    return bindActionCreators( {
        setToken: setToken,
        updateUserProfile: updateUserProfile
    }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.appData
    };

}

export default connect(mapStateToProps, matchDispatchToProps)(Profile) ;