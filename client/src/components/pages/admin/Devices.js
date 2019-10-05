import React, { Component } from 'react';
import axios from 'axios';
import {Table, Button, Icon, Modal, Form, Dropdown} from 'semantic-ui-react';
import {bindActionCreators} from "redux";
import connect from "react-redux/es/connect/connect";
import {setDevices} from "../../../actions/setDevices";
import {setCommandList} from "../../../actions/setCommandList";
import {addDevice} from "../../../actions/addDevice";
import {deleteDevice} from "../../../actions/deleteDevice";
import {toast, ToastContainer} from "react-toastify";

const buttonContainer = {
    display: 'flex',
    justifyContent: 'flex-end'
};

const deviceType = [
    'GADGET',
    'INDICATOR'
];

class Devices extends Component {

    state = {
        open: false,
        device: '',
        deviceType: '',
        command: []
    };

    componentDidMount() {
        let urlDevice = 'http://localhost:8080/device',
            urlCommand = 'http://localhost:8080/command';

        axios.get(urlDevice, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setDevices(response.data)
                }
            })
            .catch(err => console.error(err));
        

        axios.get(urlCommand, {
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
    

    addNewDevice = () => {
        if(this.state.device.length !== 0 
            && this.state.deviceType.length !== 0
            && this.state.command.length !== 0) {
            let url = 'http://localhost:8080/device',
                body = JSON.stringify({
                    name: this.state.device,
                    deviceType: this.state.deviceType,
                    commands: this.state.command
                });

            axios.post(url, body, {
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    if(response.status === 200) {
                        this.props.addDevice(JSON.parse(body));
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

    delDevice = (name, index) => {
        let url = 'http://localhost:8080/device/';

        axios.delete(url + name, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.deleteDevice(index)
                }
            })
            .catch(err => console.log(err))
    };

    devicesData() {
        if(this.props.data.length !== 0) {
            return this.props.data.map((data, index) => {
                return (
                    <Table.Body key={index}>
                        <Table.Row>
                            <Table.Cell textAlign='center'>{data.name}</Table.Cell>
                            <Table.Cell textAlign='center'>{data.deviceType}</Table.Cell>
                            <Table.Cell textAlign='center'>
                                <ul>
                                    {data.commands.map((item, index) => {
                                        return (
                                            <li key={index}>{item.name}</li>
                                        )
                                    })}
                                    
                                </ul>
                                
                            </Table.Cell>
                            <Table.Cell textAlign='center'>
                                <Button basic icon negative>
                                    <Icon name='cancel'/>
                                </Button>
                            </Table.Cell>
                            <Table.Cell textAlign='center'>
                                <Button basic icon negative onClick={() => this.delDevice(data.name, index)}>
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

    editDeviceTypeField =(event, data) => {
        this.setState({...this.state, deviceType: data.value })
    };

    editCommandField = (event, data) => { 
        this.setState({...this.state, command: data.value.map(item => {
            return {
                name: item
            }
        })})
    };

    editField = ({target}) => {
        this.setState({...this.state, [target.name]: target.value})
    };

    createDeviceTypeOptions() {
        return deviceType.map((item, index) => ({
            key: index,
            text: item,
            value: item,
        }))
    }
    
    createCommandOptions() {
        return this.props.dataCommands.map((item, index) => ({
            key: index,
            text: item.name,
            value: item.name,
            
        }));
    }

    newDeviceModal() {
        return (
            <Modal size='small' open={this.state.open} onClose={this.closeModal} closeIcon>
                <Modal.Header>Add New Device</Modal.Header>
                <Modal.Content>
                    <Form>
                        <Form.Field>
                            <label>Name</label>
                            <input type='text' name='device' onChange={this.editField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Device Type</label>
                            <Dropdown
                                fluid
                                onChange={this.editDeviceTypeField}
                                name='deviceType'
                                selection
                                options={this.createDeviceTypeOptions()}
                            />
                        </Form.Field>
                        <Form.Field>
                            <label>Commands</label>
                            <Dropdown
                                fluid
                                multiple
                                onChange={this.editCommandField}
                                name='clothTypeData'
                                search
                                selection
                                options={this.createCommandOptions()}
                            />
                        </Form.Field>
                    </Form>
                </Modal.Content>
                <Modal.Actions>
                    <Button type='submit' onClick={this.addNewDevice}>Submit</Button>
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
                            <Table.HeaderCell textAlign='center'>Device Type</Table.HeaderCell>
                            <Table.HeaderCell textAlign='center'>Commands</Table.HeaderCell>
                            <Table.HeaderCell textAlign='center'>Delete Command</Table.HeaderCell>
                            <Table.HeaderCell textAlign='center'>Delete Device</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    {this.devicesData()}
                    {this.newDeviceModal()}
                </Table>
            </div>
        )
    }
}

function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            setDevices: setDevices,
            setCommandList: setCommandList,
            addDevice: addDevice,
            deleteDevice: deleteDevice
        }, dispath);
}

function mapStateToProps(state) {
    return {
        data: state.devices,
        dataCommands: state.commandList
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(Devices);