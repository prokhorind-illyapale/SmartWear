import React, { Component } from 'react';
import axios from 'axios';
import {Table, Button, Icon, Modal, Form} from 'semantic-ui-react';
import {bindActionCreators} from "redux";
import connect from "react-redux/es/connect/connect";
import {setCommandList} from "../../../actions/setCommandList";
import {addCommand} from "../../../actions/addCommand";
import {deleteCommand} from "../../../actions/deleteCommand";
import {toast, ToastContainer} from "react-toastify";

const buttonContainer = {
    display: 'flex',
    justifyContent: 'flex-end'
};

class commandList extends Component {
    
    state = {
        open: false,
        command: ''
    };
    
    componentDidMount() {
        let url = 'http://localhost:8080/command';
        
        axios.get(url, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setCommandList(response.data)
                }
            })
            .catch(err => console.error(err))
        
    }
    
    addNewCommand = () => {
        if(this.state.command.length !== 0) {
            let url = 'http://localhost:8080/command',
                payload = {
                    name: this.state.command
                },
                body = JSON.stringify({
                    name: this.state.command
                });

            axios.post(url, body, {
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    if(response.status === 200) {
                        this.props.addCommand(payload);
                    }
                })
                .catch(err => console.error(err))
        } else {
            toast.error('Empty input field', {
                position: toast.POSITION.TOP_CENTER
            });
        }
        this.closeModal();
   
    };
    
    delCommand = (name) => {
        let url = 'http://localhost:8080/command';
        
        axios.delete(url,{
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            },
            data: {
                name: name
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.deleteCommand(name)
                }
            })
            .catch(err => console.log(err))
    };
    
    commandListData() {
        if(this.props.data.length !== 0) {
            return this.props.data.map((data, index) => {
                return (
                    <Table.Body key={index}>
                        <Table.Row>
                            <Table.Cell>{data.name}</Table.Cell>
                            <Table.Cell textAlign='center'>
                                <Button basic icon negative onClick={() => this.delCommand(data.name)}>
                                    <Icon name='cancel'/>
                                </Button>
                            </Table.Cell>
                        </Table.Row>
                    </Table.Body>
                )
            })
        }
     
    }
    
    openModal = () =>
        this.setState({open: true});

    closeModal = () =>
        this.setState({ open: false });

    editField =({target}) => {
        this.setState({...this.state, [target.name]: target.value })
    };
    
    newCommandModal() {
        return (
                <Modal size='small' open={this.state.open} onClose={this.closeModal} closeIcon>
                    <Modal.Header>Add New Command</Modal.Header>
                    <Modal.Content>
                        <Form>
                            <Form.Field>
                                <label>Name</label>
                                <input type='text' name='command' onChange={this.editField}/>
                            </Form.Field>
                        </Form>
                    </Modal.Content>
                    <Modal.Actions>
                        <Button type='submit' onClick={this.addNewCommand}>Submit</Button>
                    </Modal.Actions>
                </Modal>
            )
    }
    
    render() {
        return (
            <div>
                <ToastContainer/>
                <div style={buttonContainer}>
                    <Button basic color='blue' onClick={this.openModal}>
                        <Icon name='plus'/>
                        Add New
                    </Button>
                </div>
                <Table celled>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell width={14}>Name</Table.HeaderCell>
                            <Table.HeaderCell textAlign='center'>Delete</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    {this.commandListData()}
                    {this.newCommandModal()}
                </Table>
            </div>
        )
    }
}

function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            setCommandList: setCommandList,
            addCommand: addCommand,
            deleteCommand: deleteCommand
        }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.commandList,
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(commandList);