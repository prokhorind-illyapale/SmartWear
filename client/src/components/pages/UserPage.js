import React, { Component } from 'react';
import '../../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header} from 'semantic-ui-react';

    const styleForText ={
        color: 'black'
    };


class UserPage extends Component {


    render() {
        let userData = this.props.data.userData;
        return (
            <div>
                <Segment className="auth-container">
                    <Header>
                        Hello {userData.login}
                    </Header>
                    <p style={styleForText}>I know you live in {userData.city}</p>
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


export default connect(mapStateToProps)(UserPage);