import React, { Component } from 'react';
import Navbar from "./components/nav/Navbar";
import AuthPage from "./components/auth/AuthPage";
import Main from "./components/Main";
import { connect } from 'react-redux';
import { BrowserRouter as Router} from "react-router-dom";

import 'react-toastify/dist/ReactToastify.css';
import 'semantic-ui-css/semantic.min.css'

class App extends Component {
    state = {
        token: ''
    };

    componentDidMount() {
        if(typeof window.localStorage.token !== 'undefined') {
            this.setState({...this.state, token: window.localStorage.token})
        }

    }

  render() {

      let data = this.props.data,
            token;

        if(data.length !== 0 || typeof this.state.token === 'undefined' ) {
            token = btoa(data[0].login);
            localStorage.setItem('token', token);
        } else {
            token = this.state.token
        }

      return (
          <Router>
              <div>
                  <Navbar token={token}/>
                  {!token ? <AuthPage/> : <Main/>}
              </div>
          </Router>
    );
  }
}

function mapStateToProps(state) {
    return {
        data: state.appData
    }
}

export default connect(mapStateToProps)(App);
