import React, { Component } from 'react';
import { Route} from "react-router-dom";
import UserPage from "./UserPage";
import Admin from "./admin/Admin";
import Settings from "./settings/Settings";
import axios from "axios";
import {bindActionCreators} from "redux";
import {setUserData} from "../../actions/setUserData";
import connect from "react-redux/es/connect/connect";
import ClothPage from "./cloth/ClothPage";
import LookPage from "./look/LookPage";
import '../../styleForComponents/kit.css'



class Main extends Component {

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
                    this.props.setUserData(response.data);
                })
                .catch(err => console.log(err));

        }
    }

    render() {
        return (
            <div>
                <Route exact path="/" component={UserPage}/>
                <Route path="/admin" component={Admin}/>
                <Route path="/settings" component={Settings}/>
                <Route path="/cloth" component={ClothPage}/>
                <Route path="/look" component={LookPage}/>  
            </div>

        )
    }

}

function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            setUserData: setUserData,
        }, dispath);
}

export default connect(null, matchDispatchToProps)(Main);