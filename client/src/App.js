import React, { Component } from 'react';
import './App.css';
import 'semantic-ui-css/semantic.min.css'
import Navbar from "./components/Navbar";
import AuthPage from "./components/AuthPage";

// import * as Backend from './service/backend';

class App extends Component {
    
    // constructor(props) {
    //     super(props);

        // this.state = {
        //     greetings : ''
        // }
    // }
    
    // componentDidMount() {
    //     console.log('componentDidMount');
    //     Backend.getJson().then(response => {
    //         this.setState({
    //             greetings: response
    //         })
    //     })
    // }
  render() {
    return (
      <div className="App">
        <Navbar/>
        <AuthPage/>
      </div>
    );
  }
}

export default App;
