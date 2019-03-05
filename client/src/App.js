import React, { Component } from 'react';
import Navbar from "./components/Navbar";
import AuthPage from "./components/auth/AuthPage";
import Main from "./components/Main";

import 'react-toastify/dist/ReactToastify.css';
import 'semantic-ui-css/semantic.min.css'
class App extends Component {

    state = {
        token: ''
    };

    componentDidMount() {
        if(typeof localStorage.token !== 'undefined') {
            this.setState({...this.state, token: localStorage.token})
        }
    }

  render() {
        let token = this.state.token;
    return (
      <div>
          <Navbar token={token}/>
          {!token && <AuthPage/>}
          {token && <Main/>}
      </div>
    );
  }
}

export default App;
