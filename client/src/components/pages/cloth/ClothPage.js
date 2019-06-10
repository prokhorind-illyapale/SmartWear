import React, { Component } from 'react';
import {Button, Card, Dropdown, Icon, Image, Modal, Form, Input} from 'semantic-ui-react';
import {clothIcons} from '../../../img/clothIcons';
import axios from "axios";
import connect from "react-redux/es/connect/connect";
import {bindActionCreators} from "redux";
import {setClothAttr} from "../../../actions/setClothAttr";
import {setCloth} from "../../../actions/setCloth";
import {addClothAttr} from "../../../actions/addClothAttr";
import {deleteClothAttr} from "../../../actions/deleteClothAttr";


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
        
        axios.get(url + login + '/0', {
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
            .catch(err => console.error(err))
    };
    
    setClothData = () => {
        let data = this.props.data;
        return data.map((data, index) => {
            let idx = clothIcons.findIndex(x => x.name === data.cloth.name);
            return (
                <Card key={index}>
                    {
                        idx !== -1 
                            ? <Image src={clothIcons[idx].value}  wrapped ui={false}/> 
                            : <div className='image'>
                                <Icon name='plus' size='huge' className='addClothIcon'/>
                            </div>
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
                            }/>
                        </Card.Description>
                        <Card.Description>
                            <b>Price:</b> {data.price}$
                        </Card.Description>
                    </Card.Content>
                    <Card.Content extra textAlign='center'>
                        <Icon name='edit' color='blue' size='large'/>
                        <Icon name='delete' color='red' size='large' onClick={() => this.delClothAttr(data.code)}/>
                    </Card.Content>
                </Card>
            )
        })

    };
    
    
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
                            <input type='file' name='picture' onChange={this.editField}/>
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

    closeModal = () =>
        this.setState({ open: false });

    editField = ({target}) => {
        this.setState({...this.state, [target.name]: target.value})
    };

    editClothField = (event, data) => {
        this.setState({...this.state, cloth: this.props.dataCloth[data.value]})
    };

    editSizeField = (event, data) => {
        this.setState({...this.state, size: data.value})
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
            </div>
          
        )

    }
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        setClothAttr: setClothAttr,
        setCloth: setCloth,
        addClothAttr: addClothAttr,
        deleteClothAttr: deleteClothAttr
    }, dispatch)
}

function mapStateToProps(state) {
    return {
        data: state.clothAttr,
        dataCloth: state.cloth
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(ClothPage);