import React, { Component } from 'react';
import './App.css';
import 'semantic-ui-css/semantic.min.css'
import Navbar from "./components/Navbar";
import AuthPage from "./components/Auth/AuthPage";

// import * as Backend from './service/backend';

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            signup: false,
            signin: true,
        };
    }



    signUp = () => {
        if(this.state.signin === true) {
            this.setState({...this.state, signin: false, signup: true});
        }
    };

    signIn = () => {
        if(this.state.signup === true) {
            this.setState({...this.state, signup: false, signin: true});
        }
    };

  render() {
    return (
      <div className="App">
        <Navbar signUp={this.signUp} signIn={this.signIn} />
        <AuthPage signup={this.state.signup} signin={this.state.signin}/>
      </div>
    );
  }
}

export default App;
