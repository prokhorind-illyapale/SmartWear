import React, { Component } from 'react';
import '../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header} from 'semantic-ui-react';
import axios from 'axios';

    const styleForText ={
        color: 'black'
    };


class Main extends Component {
    state = {
        data: [],
    };

    componentDidMount() {
        if(typeof localStorage.token !== 'undefined') {
            let url = 'http://localhost:8080/user/get/',
                str = atob(localStorage.token),
                login = str.substring(0 ,str.indexOf(':'));


            axios.get(url + login, {
                headers: {
                    'Authorization': "Basic " + localStorage.token
                }
            })
                .then(response => {
                    this.setState({...this.state, data: response.data});
                })
                .catch(err => console.log(err))
        }
    }

    render() {
            return (
                <div className="auth-body">
                    <Segment className="auth-container">
                        <Header>
                            Hello {this.state.data.login}
                        </Header>
                        <p style={styleForText}>I know you live in {this.state.data.city}</p>
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