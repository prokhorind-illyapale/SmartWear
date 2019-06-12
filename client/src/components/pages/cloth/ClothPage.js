import React, { Component } from 'react';
import {Button, Card, Dropdown, Icon, Image, Modal, Form, Input, Confirm} from 'semantic-ui-react';
import {clothIcons} from '../../../img/clothIcons';
import axios from "axios";
import connect from "react-redux/es/connect/connect";
import {bindActionCreators} from "redux";
import {setClothAttr} from "../../../actions/setClothAttr";
import {setCloth} from "../../../actions/setCloth";
import {addClothAttr} from "../../../actions/addClothAttr";
import {deleteClothAttr} from "../../../actions/deleteClothAttr";
import {updateClothAttr} from "../../../actions/updateClothAttr";



const size = [
    {
        text: 'XS',
        value: 'XS',
        key: 'XS'
    },
    {
        text: 'S',
        value: 'S',
        key: 'S'
    },
    {
        text: 'M',
        value: 'M',
        key: 'M'
    },
    {
        text: 'L',
        value: 'L',
        key: 'L'
    },
    {
        text: 'XL',
        value: 'XL',
        key: 'XL'
    },
    {
        text: 'XXL',
        value: 'XXL',
        key: 'XXL'
    },
];

const buttonContainer = {
    display: 'flex',
    justifyContent: 'flex-end',
    margin: '20px 20px 0 0'
};

class ClothPage extends Component {
    
    state = {
        open: false,
        openConfirm: false,
        openEdit: false,
        picture: null,
        description: '',
        size: '',
        color: '',
        price: '',
        cloth: '',
        code: ''
    };
    
    componentDidMount() {
        let url = 'http://localhost:8080/user-cloth/',
            urlCloth = 'http://localhost:8080/cloth/',
            str = atob(window.localStorage.token),
            login = str.substring(0 ,str.indexOf(':'));
        
        axios.get(url + login + '/0?size=10', {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setClothAttr(response.data);
                }
            })
            .catch(err => console.error(err));

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
    }

    createOptions() {
        return this.props.dataCloth.map((item, index) => ({
            key: index,
            text: `Name: ${item.name} / Sex: ${item.sex.name}`,
            value: index

        }));
    }

    customizeCloth = () => {
        let url = 'http://localhost:8080/user-cloth/',
            payload = {
                picture: this.state.picture,
                description: this.state.description,
                size: this.state.size,
                color: this.state.color,
                price: this.state.price,
                cloth: this.state.cloth,
                code: this.state.code
            },
            body = JSON.stringify({
                picture: this.state.picture,
                description: this.state.description,
                size: this.state.size,
                color: this.state.color,
                price: this.state.price,
                cloth: this.state.cloth
            });
        
        axios.post(url, body, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.setState({...this.state, code: response.data});
                    this.props.addClothAttr(payload, this.state.code);
                }
            })
            .catch(err => console.error(err));
        
        this.closeModal();
    };
    
    delClothAttr = (code) => {
        let url = 'http://localhost:8080/user-cloth/';

      
        axios.delete(url + code, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.deleteClothAttr(code)
                }
            })
            .catch(err => console.error(err));
        
        this.closeConfirm();
    };
    
    setClothData = () => {
        let data = this.props.data;
        return data.map((data, index) => {
            let idx = clothIcons.findIndex(x => x.name === data.cloth.name);
            return (
                <Card key={index}>
                    {
                        data.picture !== null 
                            ? data.picture 
                            : <Image src={clothIcons[idx].value}  wrapped ui={false}/>
                    }
                    <Card.Content textAlign='center'>
                        <Card.Header>{data.description}</Card.Header>
                        <Card.Meta>
                            {data.cloth.name}
                        </Card.Meta>
                        <Card.Description>
                            <b>Size:</b> {data.size}
                        </Card.Description>
                        <Card.Description>
                            <b>Sex:</b> {data.cloth.sex.name}
                        </Card.Description>
                        <Card.Description>
                            <b>Color:</b> {data.color}
                            <div style={
                                {
                                    width: '15px', 
                                    height: '15px', 
                                    border: '1px solid rgba(177, 177, 177, 1)', 
                                    borderRadius: '2px',
                                    marginLeft: '5px',
                                    display: 'inline-block',
                                    backgroundColor: data.color
                                }
                            }
                            />
                        </Card.Description>
                        <Card.Description>
                            <b>Price:</b> {data.price}$
                        </Card.Description>
                    </Card.Content>
                    <Card.Content extra textAlign='center'>
                        <Button icon='edit' color='blue' basic onClick={() => this.openEdit(data)}/>
                        <Button icon='delete' color='red' basic onClick={this.openConfirm}/>
                    </Card.Content>
                    <Confirm
                        className='confirm_window'
                        size='mini'
                        open={this.state.openConfirm}
                        onCancel={this.closeConfirm}
                        onConfirm={() => this.delClothAttr(data.code)}
                    />
                </Card>
            )
        })

    };
    
    editClothAttr = (code) => {
        let url = 'http://localhost:8080/user-cloth/',
            body = JSON.stringify({
                picture: this.state.picture,
                description: this.state.description,
                size: this.state.size,
                color: this.state.color,
                price: this.state.price,
                cloth: this.state.cloth
            });
        
        axios.put(url + code, body, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.updateClothAttr(code, JSON.parse(body))
                }
            })
            .catch(err => console.log(err));
        
        this.closeEdit();
    };
    
    editClothModal() {
        return (
            <Modal size='small' open={this.state.openEdit} onClose={this.closeEdit} closeIcon>
                <Modal.Header>Edit Cloth</Modal.Header>
                <Modal.Content>
                    <Form>
                        <Form.Field>
                            <label>Cloth</label>
                            <Dropdown
                                fluidS
                                onChange={this.editClothField}
                                name='cloth'
                                search
                                selection
                                options={this.createOptions()}
                                defaultValue={
                                    this.props.dataCloth.findIndex(x => 
                                        x.name === this.state.cloth.name 
                                        && x.sex.name === this.state.cloth.sex.name
                                    )
                                }
                            />
                        </Form.Field>
                        <Form.Field>
                            <label>Brand/Model</label>
                            <input type='text' name='description' onChange={this.editField} value={this.state.description}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Picture</label>
                            <input type='file' name='picture' onChange={this.handleLoadLocalFile} value={this.state.picture !== null ? this.state.picture : ''}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Size</label>
                            <Dropdown
                                fluid
                                onChange={this.editSizeField}
                                name='size'
                                search
                                selection
                                options={size}
                                value={this.state.size}
                            />
                        </Form.Field>
                        <Form.Field>
                            <label>Color</label>
                            <input type='text' name='color' onChange={this.editField} value={this.state.color}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Price</label>
                            <Input icon='dollar' type='number' name='price' value={this.state.price} onChange={this.editField}/>
                        </Form.Field>
                    </Form>
                </Modal.Content>
                <Modal.Actions>
                    <Button type='submit' onClick={() => this.editClothAttr(this.state.code)}>Submit</Button>
                </Modal.Actions>
            </Modal>
        )
    }
    
    
    clothModal() {
        return (
            <Modal size='small' open={this.state.open} onClose={this.closeModal} closeIcon>
                <Modal.Header>Customize Cloth</Modal.Header>
                <Modal.Content>
                    <Form>
                        <Form.Field>
                            <label>Cloth</label>
                            <Dropdown
                                fluid
                                onChange={this.editClothField}
                                name='cloth'
                                search
                                selection
                                options={this.createOptions()}
                            />
                        </Form.Field>
                        <Form.Field>
                            <label>Brand/Model</label>
                            <input type='text' name='description' onChange={this.editField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Picture</label>
                            <input type='file' name='picture' onChange={this.handleLoadLocalFile}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Size</label>
                            <Dropdown
                                fluid
                                onChange={this.editSizeField}
                                name='size'
                                search
                                selection
                                options={size}
                            />
                        </Form.Field>
                        <Form.Field>
                            <label>Color</label>
                            <input type='text' name='color' onChange={this.editField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Price</label>
                            <Input icon='dollar' type='number' name='price' onChange={this.editField}/>
                        </Form.Field>
                    </Form>
                </Modal.Content>
                <Modal.Actions>
                    <Button type='submit' onClick={this.customizeCloth}>Submit</Button>
                </Modal.Actions>
            </Modal>
        )
    }

    openModal = () =>
        this.setState({open: true});
    
    openConfirm = () => 
        this.setState({openConfirm: true});
    
    openEdit = ({description, size, price, picture, cloth, color, code}) => 
        this.setState({...this.state, openEdit: true, description, size, price, picture, cloth, color, code});

    closeModal = () =>
        this.setState({ open: false });
    
    closeConfirm = () =>
        this.setState({openConfirm: false});

    closeEdit = () =>
        this.setState({openEdit: false});

    editField = ({target}) => {
        this.setState({...this.state, [target.name]: target.value})
    };

    editClothField = (event, data) => {
        this.setState({...this.state, cloth: this.props.dataCloth[data.value]})
    };

    editSizeField = (event, data) => {
        this.setState({...this.state, size: data.value})
    };

    handleLoadLocalFile = (event) => {
        event.preventDefault();
        const file = event.target.files[0];
        const localImageUrl = window.URL.createObjectURL(file);
        this.setState({...this.state, picture: localImageUrl})
    };

    render() {
        return(
            <div>
                <div style={buttonContainer}>
                    <Button  onClick={this.openModal}>
                        <Icon name='wrench'/>
                        Customize Cloth
                    </Button>
                </div>
                <Card.Group itemsPerRow={5}>
                    {this.setClothData()}
                </Card.Group>
                {this.clothModal()}
                {this.editClothModal()}
            </div>
          
        )

    }
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        setClothAttr: setClothAttr,
        setCloth: setCloth,
        addClothAttr: addClothAttr,
        deleteClothAttr: deleteClothAttr,
        updateClothAttr: updateClothAttr
    }, dispatch)
}

function mapStateToProps(state) {
    return {
        data: state.clothAttr,
        dataCloth: state.cloth
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(ClothPage);