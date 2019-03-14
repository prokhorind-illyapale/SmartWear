import React, { Component } from 'react';
import { Route} from "react-router-dom";
import UserPage from "./UserPage";
import Admin from "./Admin";
import axios from "axios";
import {bindActionCreators} from "redux";
import {setUserData} from "../../actions/setUserData";
import connect from "react-redux/es/connect/connect";



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
        return (
            <div>
                <Route exact path="/" component={UserPage}/>
                <Route path="/admin" component={Admin}/>
            </div>
        )
    }

}

function matchDispatchToProps(dispath) {
    return bindActionCreators( { setUserData: setUserData }, dispath);
}

export default connect(null, matchDispatchToProps)(Main);