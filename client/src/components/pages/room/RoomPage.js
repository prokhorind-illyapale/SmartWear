import React,  {Component} from "react";
import {Button, Icon, Modal, Form, Header, Segment, Table, List, Label, Confirm} from 'semantic-ui-react';
import connect from "react-redux/es/connect/connect";
import axios from "axios";
import {bindActionCreators} from "redux";
import {setRoom} from "../../../actions/setRoom";
import {addRoom} from "../../../actions/addRoom";
import {setDeviceInRoom} from "../../../actions/setDeviceInRoom";
import {deleteRoom} from "../../../actions/deleteRoom";
// import '../../../styleForComponents/Look.css';
// import '../../../styleForComponents/kit.css';


class RoomPage extends Component {

    state = {
        isModalOpen: false,
        isConfirmOpen: false,
        roomNameDel: '',
        activeDevice: 0,
        roomName: '',
    }

    componentDidMount() {
        let url = 'http://localhost:8080/room/user';
    
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

    editField = ({target}) => {
        this.setState({[target.name]: target.value})
    };


    openModal = () => this.setState({isModalOpen: true});

    closeModal = () => this.setState({isModalOpen: false});

    openConfirm = () => {
        this.setState({ isConfirmOpen: true })
    };

    closeConfirm = () => {
        this.setState({ isConfirmOpen: false })
    };

    setDeviceData = () => {
        let dataDevice = this.props.dataDeviceInRoom;
        return dataDevice.map((item, index) => {
            return (
                <Table.Row key={index}>
                    <Table.Cell>
                    <Label ribbon>{item.device.name}</Label>
                    </Table.Cell>
                    <Table.Cell>{item.device.deviceType}</Table.Cell>
                    <Table.Cell>
                        <List celled ordered>
                            {item.device.commands.map((command, index) => {
                                   return (
                                        <List.Item key={index}>{command.name}</List.Item>
                                   ) 
                            })}
                        </List>
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
                                    <Table.HeaderCell>Device Name</Table.HeaderCell>
                                    <Table.HeaderCell>Device Type</Table.HeaderCell>
                                    <Table.HeaderCell>Commands</Table.HeaderCell>
                                    </Table.Row>
                                </Table.Header>
                                <Table.Body>
                                    {this.setDeviceData()}
                                </Table.Body>
                            </Table>
                                :
                            <Button positive onClick={() => this.setDeviceInRoom(item.roomName, index)}>Click To Get Your Device</Button>
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
                    <Button  onClick={this.openModal}>
                        <Icon name='plus'/>
                        Create New Room
                    </Button>
                </div>
                {this.setRooms()}
                {this.roomModal()}
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
    }, dispatch)
}

function mapStateToProps(state) {
    return {
        data: state.room,
        dataDeviceInRoom: state.deviceInRoom,
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(RoomPage);