import React, { Component } from 'react';
import '../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header} from 'semantic-ui-react';
import axios from 'axios';
import {bindActionCreators} from "redux";
import {setUserData} from "../actions/setUserData";

    const styleForText ={
        color: 'black'
    };


class Main extends Component {

    componentDidMount() {
        if(typeof window.localStorage.token !== 'undefined') {
            let url = 'http://localhost:8080/user/get/',
                str = atob(window.localStorage.token),
                login = str.substring(0 ,str.indexOf(':'));

            axios.get(url + login, {
                headers: {
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    this.props.setUserData(response.data)
                })
                .catch(err => console.log(err))
        }
    }

    render() {
        let userData = this.props.data.userData;
        return (
            <div className="auth-body">
                <Segment className="auth-container">
                    <Header>
                        Hello {userData.login}
                    </Header>
                    <p style={styleForText}>I know you live in {userData.city}</p>
                </Segment>
            </div>
        )

    }

}

function mapStateToProps(state) {
    return {
        data: state.appData
    }
}

function matchDispatchToProps(dispath) {
    return bindActionCreators( { setUserData: setUserData }, dispath);
}

export default connect(mapStateToProps, matchDispatchToProps)(Main);