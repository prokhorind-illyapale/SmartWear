import React, { Component } from 'react';
import Navbar from "./components/nav/Navbar";
import AuthPage from "./components/auth/AuthPage";
import Main from "./components/pages/Main";
import { connect } from 'react-redux';
import {BrowserRouter as Router ,Switch} from "react-router-dom";
import 'weather-icons/css/weather-icons.css';
import 'react-toastify/dist/ReactToastify.css';
import 'semantic-ui-css/semantic.min.css'

class App extends Component {
    state = {
        token: '',
        isOpen: false,
        icon: 'bars'
    };

    componentDidMount() {
        if(typeof window.localStorage.token !== 'undefined') {
            this.setState({...this.state, token: window.localStorage.token})
        }

    }

    showMenu = () => {
        this.state.icon === 'bars'
            ?
            this.setState({...this.state, icon: 'cancel', isOpen: true})
            :
            this.setState({...this.state, icon: 'bars', isOpen: false});
    };

  render() {

      let data = this.props.data,
            token;
        if(data.length !== 0 || typeof this.state.token === 'undefined' ) {
            token = data.auth.token;
            localStorage.setItem('token', token);
        } else {
            token = this.state.token
        }

      return (
          <Router>
              <div className='App'>
                  <Navbar token={token} isOpen={this.state.isOpen} showMenu={this.showMenu} icon={this.state.icon}/>
                  <Switch>
                  {!token ? <AuthPage/> : <Main isOpen={this.state.isOpen} showMenu={this.showMenu}/>}
                  </Switch>
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
