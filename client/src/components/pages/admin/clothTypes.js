import React, { Component } from 'react';
import axios from 'axios';
import {Table, Button, Icon, Modal, Form} from 'semantic-ui-react';
import {bindActionCreators} from "redux";
import connect from "react-redux/es/connect/connect";
import {setClothTypes} from "../../../actions/setClothTypes";
import {addClothType} from "../../../actions/addClothType";
import {deleteClothType} from "../../../actions/deleteClothType";
import {toast, ToastContainer} from "react-toastify";

const buttonContainer = {
    display: 'flex',
    justifyContent: 'flex-end'
};

class clothTypes extends Component {
    
    state = {
        open: false,
        clothtype: ''
    };
    
    componentDidMount() {
        let url = 'http://localhost:8080/cloth/type';
        
        axios.get(url, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setClothTypes(response.data)
                }
            })
            .catch(err => console.error(err))
        
    }
    
    addNewClothType = () => {
        if(this.state.clothtype.length !== 0) {
            let url = 'http://localhost:8080/cloth/type/',
                payload = {
                    name: this.state.clothtype
                },
                body = JSON.stringify({
                    name: this.state.clothtype
                });

            axios.post(url, body, {
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    if(response.status === 200) {
                        this.props.addClothType(payload);
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
    
    delClothType = (name) => {
        let url = 'http://localhost:8080/cloth/type/';
        
        axios.delete(url + name, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.deleteClothType(name)
                }
            })
            .catch(err => console.log(err))
    };
    
    clothTypesData() {
        if(this.props.data.length !== 0) {
            return this.props.data.map((data, index) => {
                return (
                    <Table.Body key={index}>
                        <Table.Row>
                            <Table.Cell>{data.name}</Table.Cell>
                            <Table.Cell textAlign='center'>
                                <Button basic icon negative onClick={() => this.delClothType(data.name)}>
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
    
    newClothTypesModal() {
        return (
                <Modal size='small' open={this.state.open} onClose={this.closeModal} closeIcon>
                    <Modal.Header>Add New Cloth Type</Modal.Header>
                    <Modal.Content>
                        <Form>
                            <Form.Field>
                                <label>Name</label>
                                <input type='text' name='clothtype' onChange={this.editField}/>
                            </Form.Field>
                        </Form>
                    </Modal.Content>
                    <Modal.Actions>
                        <Button type='submit' onClick={this.addNewClothType}>Submit</Button>
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
                    {this.clothTypesData()}
                    {this.newClothTypesModal()}
                </Table>
            </div>
        )
    }
}

function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            setClothTypes: setClothTypes,
            addClothType: addClothType,
            deleteClothType: deleteClothType
        }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.clothTypes,
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(clothTypes);