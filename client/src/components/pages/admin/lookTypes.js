import React, { Component } from 'react';
import axios from 'axios';
import {Table, Button, Icon, Modal, Form} from 'semantic-ui-react';
import {bindActionCreators} from "redux";
import connect from "react-redux/es/connect/connect";
import {setLookTypes} from "../../../actions/setLookTypes";
import {addLookType} from "../../../actions/addLookType";
import {deleteLookType} from "../../../actions/deleteLookType";
import {toast, ToastContainer} from "react-toastify";

const buttonContainer = {
    display: 'flex',
    justifyContent: 'flex-end'
};

class lookTypes extends Component {

    state = {
        open: false,
        looktype: ''
    };

    componentDidMount() {
        let url = 'http://localhost:8080/look-type';

        axios.get(url, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setLookTypes(response.data)
                }
            })
            .catch(err => console.error(err))

    }

    addNewLookType = () => {
        if(this.state.looktype.length !== 0) {
            let url = 'http://localhost:8080/look-type',
                payload = {
                    name: this.state.looktype
                },
                body = JSON.stringify({
                    name: this.state.looktype
                });

            axios.post(url, body, {
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    if(response.status === 200) {
                        this.props.addLookType(payload);
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

    delLookType = (name) => {
        let url = 'http://localhost:8080/look-type/';

        axios.delete(url + name, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.deleteLookType(name)
                }
            })
            .catch(err => console.log(err))
    };

    lookTypesData() {
        if(this.props.data.length !== 0) {
            return this.props.data.map((data, index) => {
                return (
                    <Table.Body key={index}>
                        <Table.Row>
                            <Table.Cell>{data.name}</Table.Cell>
                            <Table.Cell textAlign='center'>
                                <Button basic icon negative onClick={() => this.delLookType(data.name)}>
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

    newLookTypesModal() {
        return (
            <Modal size='small' open={this.state.open} onClose={this.closeModal} closeIcon>
                <Modal.Header>Add New Look Type</Modal.Header>
                <Modal.Content>
                    <Form>
                        <Form.Field>
                            <label>Name</label>
                            <input type='text' name='looktype' onChange={this.editField}/>
                        </Form.Field>
                    </Form>
                </Modal.Content>
                <Modal.Actions>
                    <Button type='submit' onClick={this.addNewLookType}>Submit</Button>
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
                    {this.lookTypesData()}
                    {this.newLookTypesModal()}
                </Table>
            </div>
        )
    }
}

function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            setLookTypes: setLookTypes,
            addLookType: addLookType,
            deleteLookType: deleteLookType
        }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.lookTypes,
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(lookTypes);