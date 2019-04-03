import React, { Component } from 'react';
import {Button, Message, Icon, Header, Confirm} from 'semantic-ui-react';
import {bindActionCreators} from "redux";
import {logOut} from "../../../actions/logOut";
import connect from "react-redux/es/connect/connect";
import axios from "axios";
import {toast, ToastContainer} from "react-toastify";

const messageContent = {
    display: 'flex',
    flexDirection: 'column',

};


class Account extends Component {

    state = {
        openConfirm: false
    };

    openConfirm = () => {
        this.setState({ openConfirm: true })
    };

    closeConfirm = () => {
        this.setState({ openConfirm: false })
    };

    deleteAccount = (login) => {
        this.closeConfirm();
        let url = 'http://localhost:8080/user/delete/';
        axios.delete(url + login, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(data => {
                if(data.status === 200) {
                    this.props.logOut();
                    this.props.history.push('/');
                    toast.success('See you soon :)', {
                        position: toast.POSITION.TOP_CENTER
                    });
                }
            })
            .catch(err => console.log(err))
    };

    render() {
        let data = this.props.myData.userData;
        return (
            <div>
                <ToastContainer/>
                <Message icon error>
                    <Icon name='warning sign'/>
                    <div style={messageContent}>
                        <Header>Attention!</Header>
                        <p>Think twice before make this</p>
                    </div>
                </Message>
                <Button negative onClick={this.openConfirm}>Delete my account</Button>
                <Confirm
                    className='confirm_window'
                    size='mini'
                    open={this.state.openConfirm}
                    onCancel={this.closeConfirm}
                    onConfirm={() => this.deleteAccount(data.login)}
                />
            </div>

        )
    }
}

function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            logOut: logOut
        }, dispath);
}

function mapStateToProps(state) {
    return {
        myData: state.appData
    }
}


export default connect(mapStateToProps, matchDispatchToProps)(Account);