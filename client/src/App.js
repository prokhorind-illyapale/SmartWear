import React, { Component } from 'react';
import Navbar from "./components/Navbar";
import AuthPage from "./components/auth/AuthPage";
import Main from "./components/Main";
import { connect } from 'react-redux';
import 'react-toastify/dist/ReactToastify.css';
import 'semantic-ui-css/semantic.min.css'

class App extends Component {

  render() {
        let data = this.props.data;
        let token = data.login;
      return (
      <div>
          <Navbar token={token}/>
          {!token ? <AuthPage/> : <Main/>}
      </div>
    );
  }
}

function mapStateToProps(state) {
    return {
        data: state.appData
    }
}

export default connect(mapStateToProps)(App);
