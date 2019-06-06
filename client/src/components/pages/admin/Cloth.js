import React, { Component } from 'react';
import axios from 'axios';
import {Table, Button, Icon, Modal, Form, Dropdown} from 'semantic-ui-react';
import {bindActionCreators} from "redux";
import connect from "react-redux/es/connect/connect";
import {setCloth} from "../../../actions/setCloth";
import {setClothTypes} from "../../../actions/setClothTypes";
import {addCloth} from "../../../actions/addCloth";
import {deleteCloth} from "../../../actions/deleteCloth";
import {toast, ToastContainer} from "react-toastify";

const buttonContainer = {
    display: 'flex',
    justifyContent: 'flex-end'
};

class Cloth extends Component {

    state = {
        open: false,
        cloth: '',
        clothTypeData: '',
        sex: ''
    };

    componentDidMount() {
        let urlCloth = 'http://localhost:8080/cloth/',
            urlClothType = 'http://localhost:8080/cloth/type';

        axios.get(urlCloth, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setCloth(response.data)
                }
            })
            .catch(err => console.error(err));
        

        axios.get(urlClothType, {
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
    

    addNewCloth = () => {
        if(this.state.cloth.length !== 0 
            && this.state.clothTypeData.length !== 0
            && this.state.sex.length !== 0) {
            let url = 'http://localhost:8080/cloth/',
                body = JSON.stringify({
                    name: this.state.cloth,
                    clothTypeData: this.state.clothTypeData,
                    sex: this.state.sex
                });

            axios.post(url, body, {
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    if(response.status === 200) {
                        this.props.addCloth(JSON.parse(body));
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

    delCloth = (name, sex, index) => {
        let url = 'http://localhost:8080/cloth/';

        axios.delete(url + name + '/' + sex, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.deleteCloth(index)
                }
            })
            .catch(err => console.log(err))
    };

    clothData() {
        if(this.props.data.length !== 0) {
            return this.props.data.map((data, index) => {
                return (
                    <Table.Body key={index}>
                        <Table.Row>
                            <Table.Cell textAlign='center'>{data.name}</Table.Cell>
                            <Table.Cell textAlign='center'>{data.clothTypeData.name}</Table.Cell>
                            <Table.Cell textAlign='center'>{data.sex.name}</Table.Cell>
                            <Table.Cell textAlign='center'>
                                <Button basic icon negative onClick={() => this.delCloth(data.name, data.sex.name, index)}>
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

    editClothField =({target}) => {
        this.setState({...this.state, [target.name]: target.value })
    };

    editClothTypeField = (event, data) => {
        this.setState({...this.state, clothTypeData: {name: data.value}})
    };

    editField = ({target}) => {
        this.setState({...this.state, [target.name]: {name: target.value}})
    };
    
    createOptions() {
        return this.props.dataClothTypes.map((item, index) => ({
            key: index,
            text: item.name,
            value: item.name
            
        }));
    }

    newClothModal() {
        return (
            <Modal size='small' open={this.state.open} onClose={this.closeModal} closeIcon>
                <Modal.Header>Add New Cloth</Modal.Header>
                <Modal.Content>
                    <Form>
                        <Form.Field>
                            <label>Name</label>
                            <input type='text' name='cloth' onChange={this.editClothField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Cloth Type</label>
                            <Dropdown
                                fluid
                                onChange={this.editClothTypeField}
                                name='clothTypeData'
                                search
                                selection
                                options={this.createOptions()}
                            />
                        </Form.Field>
                        <Form.Field>
                            <label>Sex</label>
                            <input type='text' name='sex' onChange={this.editField}/>
                        </Form.Field>
                    </Form>
                </Modal.Content>
                <Modal.Actions>
                    <Button type='submit' onClick={this.addNewCloth}>Submit</Button>
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
                            <Table.HeaderCell textAlign='center'>Name</Table.HeaderCell>
                            <Table.HeaderCell textAlign='center'>Cloth Type</Table.HeaderCell>
                            <Table.HeaderCell textAlign='center'>Sex</Table.HeaderCell>
                            <Table.HeaderCell textAlign='center'>Delete</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    {this.clothData()}
                    {this.newClothModal()}
                </Table>
            </div>
        )
    }
}

function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            setCloth: setCloth,
            setClothTypes: setClothTypes,
            addCloth: addCloth,
            deleteCloth: deleteCloth
        }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.cloth,
        dataClothTypes: state.clothTypes
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(Cloth);