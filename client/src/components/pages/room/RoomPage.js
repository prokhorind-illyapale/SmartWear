import React,  {Component} from "react";
import {Button, Icon, Modal, Form, Header, Segment, Table, List, Label, Confirm, Dropdown} from 'semantic-ui-react';
import connect from "react-redux/es/connect/connect";
import axios from "axios";
import {bindActionCreators} from "redux";
import {setRoom} from "../../../actions/setRoom";
import {addRoom} from "../../../actions/addRoom";
import {setDeviceInRoom} from "../../../actions/setDeviceInRoom";
import {deleteRoom} from "../../../actions/deleteRoom";
import {addDeviceInRoom} from "../../../actions/addDeviceInRoom";
import {setCommandList} from "../../../actions/setCommandList";
// import '../../../styleForComponents/Look.css';
// import '../../../styleForComponents/kit.css';

const deviceArr = [
    'gadget',
    'indicator'
];

class RoomPage extends Component {

    state = {
        isModalOpen: false,
        isModalDeviceOpen: false,
        isConfirmOpen: false,
        showComandsField: false,
        roomNameDel: '',
        activeDevice: 0,
        roomName: '',
        roomNameForDevice: '',
        device: '',
        deviceName: '',
        devicePin: '',
        deviceValueType: '',
        // command: [],
        certainIndicatorsValueInRoom: [],
        isModalIndicatorValueOpen: false,
    }

    componentDidMount() {
        let url = 'http://localhost:8080/room/user',
            urlCommand = 'http://localhost:8080/command';
    
        axios.get(url, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setRoom(response.data);
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
            .catch(err => console.error(err));
    }

    roomModal() {
        return (
            <Modal size='small' open={this.state.isModalOpen} onClose={this.closeModal} closeIcon>
                <Modal.Header>Create Room</Modal.Header>
                <Modal.Content>
                    <Form>
                        <Form.Field>
                            <label>Room Name</label>
                            <input type='text' name='roomName' onChange={this.editField}/>
                        </Form.Field>
                    </Form>
                </Modal.Content>
                <Modal.Actions>
                    <Button type='submit' onClick={this.addRoom}>Submit</Button>
                </Modal.Actions>
            </Modal>
        )
    }

    indicatorValueModal() {
        return (
            <Modal size='small' open={this.state.isModalIndicatorValueOpen} onClose={this.closeIndicatorValueModal} closeIcon>
                <Modal.Header>Indicator Value Is</Modal.Header>
                <Modal.Content>
                    <label>
                        {
                            this.state.certainIndicatorsValueInRoom.length !== 0 
                            ?
                            `Value: ${this.state.certainIndicatorsValueInRoom[0].value}`
                            :
                            'No Value'
                        }
                    </label>
                </Modal.Content>
            </Modal>
        )
    }

    addDeviceModal() {
        return (
            <Modal size='small' open={this.state.isModalDeviceOpen} onClose={this.closeModal} closeIcon>
                <Modal.Header>Add Device To Room</Modal.Header>
                <Modal.Content>
                    <Form>
                        <Form.Field>
                            <label>Room Name</label>
                            <Dropdown
                                fluid
                                onChange={this.editRoomField}
                                name='roomNameForDevice'
                                selection
                                options={this.createRoomOptions()}
                            />
                        </Form.Field>
                        <Form.Field>
                            <label>Device</label>
                            <Dropdown
                                fluid
                                onChange={this.editDeviceField}
                                name='device'
                                selection
                                options={this.createDeviceOptions()}
                            />
                        </Form.Field>
                        {/* {
                            this.state.showComandsField &&
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
                        } */}
                        <Form.Field>
                            <label>Device Name</label>
                            <input type='text' name='deviceName' onChange={this.editField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Pin</label>
                            <input type='number' name='devicePin' onChange={this.editField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Value Type</label>
                            <input type='text' name='deviceValueType' onChange={this.editField}/>
                        </Form.Field>
                    </Form>
                </Modal.Content>
                <Modal.Actions>
                    <Button type='submit' onClick={this.addDeviceToRoom}>Submit</Button>
                </Modal.Actions>
            </Modal>
        )
    }

    addRoom = () => {
        let url = 'http://localhost:8080/room',
            body = JSON.stringify({
                roomName: this.state.roomName,
            });
        
        axios.post(url, body, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.addRoom(JSON.parse(body));
                }
            })
            .catch(err => console.error(err));
        
        this.closeModal();
    }

    addDeviceToRoom = () => {
        let url = 'http://localhost:8080/user-device/',
            payload = {
                room: {
                    roomName: this.state.roomNameForDevice
                },
                device: {
                    name: this.state.device,
                    deviceType: this.state.device.toUpperCase(),
                    commands: ['Enable', 'Disable']
                },
                name: this.state.deviceName,
                valueType: this.state.deviceValueType,
                pin: this.state.devicePin
            },
            body = JSON.stringify({
                room: this.state.roomNameForDevice,
                device: this.state.device,
                name: this.state.deviceName,
                pin: this.state.devicePin,
                valueType: this.state.deviceValueType
            });

        axios.post(url, body, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.addDeviceInRoom(payload)
                }
            })
            .catch(err => console.error(err));

            this.closeModal();
    }

    deleteRoom = (name) => {
        let url = 'http://localhost:8080/room'

        axios.delete(url, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            },
            data: {
                roomName: name
            }
        })
        .then(response => {
            if(response.status === 200) {
                this.props.deleteRoom(name);
            }
        })
        .catch(err => console.error(err));

        this.closeConfirm();
    }

    setDeviceInRoom = (name, index) => {
        let url = `http://localhost:8080/user-device/${name}/room`;

        axios.get(url, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setDeviceInRoom(response.data);
                    this.setState({...this.state, activeDevice: index + 1})
                }
            })
            .catch(err => console.error(err));
    }

    getCertainIndicatorVal = (name) => {
        let url = `http://localhost:8080/indicator?deviceName=${name}`;

        axios.get(url, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
        .then(response => {
            if(response.status === 200) {
                console.log(response.data)
                this.setState({certainIndicatorsValueInRoom: [{value: response.data.value}], isModalIndicatorValueOpen: true});
            }
        })
        .catch(err => console.error(err));
    }

    editField = ({target}) => {
        this.setState({[target.name]: target.value})
    };

    editDeviceField =(event, data) => {
        
        this.setState({...this.state, device: data.value, showComandsField: data.value === 'gadget' ? true : false })
    };

    editRoomField =(event, data) => {
        this.setState({...this.state, roomNameForDevice: data.value })
    };

    editCommandField = (event, data) => { 
        this.setState({...this.state, command: data.value.map(item => {
            return {
                name: item
            }
        })})
    };

    openModal = (name) => {
       name === 'room' ? this.setState({isModalOpen: true}) : this.setState({isModalDeviceOpen: true});
    } 

    closeModal = () => this.setState({isModalOpen: false, isModalDeviceOpen: false});

    openConfirm = () => {
        this.setState({ isConfirmOpen: true })
    };

    closeConfirm = () => {
        this.setState({ isConfirmOpen: false })
    };

    closeIndicatorValueModal = () => {
        this.setState({isModalIndicatorValueOpen: false})
    };

    createRoomOptions() {
        return this.props.data.map((item, index) => ({
            key: index,
            text: item.roomName,
            value: item.roomName,
        }))
    }

    createDeviceOptions() {
        return deviceArr.map((item, index) => ({
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

    setDeviceData = () => {
        let dataDevice = this.props.dataDeviceInRoom;
        
        return dataDevice.map((item, index) => {
            return (
                <Table.Row key={index}>
                    <Table.Cell>
                    <Label ribbon>{item.name}</Label>
                    </Table.Cell>
                    <Table.Cell>{item.device.name}</Table.Cell>
                    <Table.Cell>{item.device.deviceType}</Table.Cell>
                    <Table.Cell textAlign="center">
                        <List celled ordered>
                            {
                                item.device.deviceType === 'GADGET' ? 
                                item.device.commands.map((command, index) => {
                                   return (
                                        <List.Item key={index}>{command.name}</List.Item>
                                   )  
                            }) :
                            <Icon name="close"/>
                        }
                        </List>
                    </Table.Cell>
                    <Table.Cell textAlign="center">
                        {
                            item.device.deviceType === 'INDICATOR' ? 
                                <Button 
                                    positive 
                                    onClick={() => this.getCertainIndicatorVal(item.name)}
                                >
                                Get Value</Button> 
                                :
                                <Icon name="close"/>
                        }
                    </Table.Cell>
              </Table.Row>
            )
        })
    }

    setRooms = () => {
        let data = this.props.data;
        return data.map((item, index) => {
            return(
                <div className="look-block" key={index}>
                    <Header as='h2' attached='top'>
                        {item.roomName}
                    </Header>
                    <Segment attached>
                        {
                            this.props.dataDeviceInRoom.length !== 0 && this.state.activeDevice === index + 1 ? 
                            <Table celled>
                                <Table.Header>
                                    <Table.Row>
                                    <Table.HeaderCell>Name</Table.HeaderCell>
                                    <Table.HeaderCell>Device Name</Table.HeaderCell>
                                    <Table.HeaderCell>Device Type</Table.HeaderCell>
                                    <Table.HeaderCell textAlign="center">Commands</Table.HeaderCell>
                                    <Table.HeaderCell textAlign="center">Value</Table.HeaderCell>
                                    </Table.Row>
                                </Table.Header>
                                <Table.Body>
                                    {this.setDeviceData()}
                                </Table.Body>
                                <Table.Footer>
                                    <Table.Row>
                                        <Table.HeaderCell colSpan='5'>
                                            <Button positive>Get All Indicator Value</Button>
                                        </Table.HeaderCell>
                                    </Table.Row>
                                </Table.Footer>
                            </Table>
                                :
                            <Button positive onClick={() => this.setDeviceInRoom(item.roomName, index)}>Get Your Devices</Button>
                        }
                        <Button negative onClick={() =>  {
                            this.setState({...this.state, isConfirmOpen: true, roomNameDel: item.roomName});
                            }}
                        >
                            Delete This Room
                        </Button>
                    </Segment>
                </div>
            )
        })
    }


    render() {
        return (
            <div className="column centered">
                <div className="row w100 end mt20">
                    {
                        this.props.data.length !== 0 && 
                        <Button className="ml10" onClick={() => this.openModal('device')}>
                            <Icon name='plus'/>
                            Add Device To Room
                        </Button>
                    }
                    <Button  onClick={() => this.openModal('room')}>
                        <Icon name='plus'/>
                        Create New Room
                    </Button>
                </div>
                {this.setRooms()}
                {this.roomModal()}
                {this.addDeviceModal()}
                {this.indicatorValueModal()}
                <Confirm
                    className='confirm_window'
                    size='mini'
                    open={this.state.isConfirmOpen}
                    onCancel={this.closeConfirm}
                    onConfirm={() => this.deleteRoom(this.state.roomNameDel)}
                />
            </div>
        ) 
    }
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        setRoom: setRoom,
        addRoom: addRoom,
        setDeviceInRoom: setDeviceInRoom,
        deleteRoom: deleteRoom,
        addDeviceInRoom: addDeviceInRoom,
        setCommandList: setCommandList,
    }, dispatch)
}

function mapStateToProps(state) {
    return {
        data: state.room,
        dataDeviceInRoom: state.deviceInRoom,
        dataCommands: state.commandList
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(RoomPage);