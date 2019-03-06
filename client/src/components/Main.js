import React, { Component } from 'react';
import '../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header} from 'semantic-ui-react';

class Main extends Component {

    render() {
        return this.props.data.map((data) => {
            return (
                <div className="auth-body">
                    <Segment>
                        <Header>
                            Hello {data.login}
                        </Header>
                    </Segment>
                </div>
            )
        })

    }

}

function mapStateToProps(state) {
    return {
        data: state.appData
    }
}
export default connect(mapStateToProps)(Main);