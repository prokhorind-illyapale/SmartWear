import React, { Component } from 'react';
import '../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header} from 'semantic-ui-react';
import axios from 'axios';

class Main extends Component {

    componentDidMount() {
        if(typeof localStorage.token !== 'undefined') {
            let url = 'http://localhost:8080/user/get/',
                login = atob(localStorage.token);

            axios.get(url + login)
                .then(response => console.log(response))
                .catch(err => console.log(err))
        }
    }

    render() {
        return (
            <div className="auth-body">
                <Segment>
                    <Header>
                        Hello
                    </Header>
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
export default connect(mapStateToProps)(Main);