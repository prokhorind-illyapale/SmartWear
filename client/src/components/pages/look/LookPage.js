import React,  {Component} from "react";
import {Button, Card, Dropdown, Icon, Image, Modal, Form, Input, Confirm, Header, Segment, Comment, Checkbox} from 'semantic-ui-react';
import connect from "react-redux/es/connect/connect";
import axios from "axios";
import {bindActionCreators} from "redux";
import {setLook} from "../../../actions/setLook";
import {setLike} from "../../../actions/setLike";
import '../../../styleForComponents/Look.css';
import '../../../styleForComponents/kit.css';
import {clothIcons} from '../../../img/clothIcons';
import {setLookTypes} from "../../../actions/setLookTypes";
import {setClothAttr} from "../../../actions/setClothAttr";
import {addNewLook} from "../../../actions/addNewLook";


class LookPage extends Component {

    state = {
        showComments: false,
        activeComment: 0,
        isModalOpen: false,
        description: '',
        public: false,
        minTemp: '',
        maxTemp: '',
        lookType: [],
        cloth: [],
        picture: null

    }

    componentDidMount() {
        let url = 'http://localhost:8080/look/get/all/',
            str = atob(window.localStorage.token),
            login = str.substring(0 ,str.indexOf(':')),
            urlLookType = 'http://localhost:8080/look-type',
            urlClothAttr = 'http://localhost:8080/user-cloth/';
    
        axios.get(url + login, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setLook(response.data);
                }
            })
            .catch(err => console.error(err));
         

        axios.get(urlLookType, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    this.props.setLookTypes(response.data)
                }
            })
            .catch(err => console.error(err));

        axios.get(urlClothAttr + login + '/0?size=1000', {
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
    }

    createLookTypeOptions() {
        return this.props.dataLookType.map((item, index) => ({
            key: index,
            text: item.name,
            value: item.name,
        }));
    }

    createClothOptions() {
        return this.props.dataCloth.map((item, index) => ({
            key: index,
            text: `${item.cloth.name}: ${item.description}`,
            value: item.code,
        }));
    }

    showComments = (index) => {
        this.state.showComments === false ? this.setState({showComments: true, activeComment: index + 1}) : this.setState({showComments: false, activeComment: 0})
    }

    setLIkeToLook = (id, like) => {
        let url = 'http://localhost:8080/look/like/';

            axios.post(url + id, {
                headers: {
                    'Authorization': "Basic " + window.localStorage.token
                }
            })
                .then(response => {
                    if(response.status === 200) {
                        this.props.setLike(like + 1);
                    }
                })
                .catch(err => console.error(err));
    }

    setClothData = (item) => {
            return item.userClothAttributes.map((data, index) => {
                let clothName = data.cloth.name.charAt(0).toUpperCase() + data.cloth.name.slice(1),
                idx = clothIcons.findIndex(x => x.name === clothName);

                return (
                    <Card className="look-cloth-element" key={index}>
                        <Image src={clothIcons[idx].value}  wrapped ui={false}/>
                        <Card.Content textAlign='center'>
                            <Card.Header>{data.description}</Card.Header>
                            <Card.Meta>
                                {clothName}
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
                    </Card>
                )
            })
    }

    lookModal() {
        return (
            <Modal size='small' open={this.state.isModalOpen} onClose={this.closeModal} closeIcon>
                <Modal.Header>Create Look</Modal.Header>
                <Modal.Content>
                    <Form>
                        <Form.Field>
                            <label>Description</label>
                            <input type='text' name='description' onChange={this.editField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Min Temp</label>
                            <input type='number' name='minTemp' onChange={this.editField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Max Temp</label>
                            <input type='number' name='maxTemp' onChange={this.editField}/>
                        </Form.Field>
                        <Form.Field>
                            <label>Choose your look type</label>
                            <Dropdown
                                fluid
                                onChange={this.editLookTypeField}
                                multiple
                                name='lookType'
                                search
                                options={this.createLookTypeOptions()}
                                selection
                            />
                        </Form.Field>
                        <Form.Field>
                            <label>Make visible for other people?</label>
                            <Checkbox name="public" onChange={this.editCheckboxField} toggle />
                        </Form.Field>
                        <Form.Field>
                            <label>Your Cloth</label>
                            <Dropdown
                                fluid
                                multiple
                                onChange={this.editClothField}
                                name='cloth'
                                search
                                options={this.createClothOptions()}
                                selection
                            />
                        </Form.Field>
                    </Form>
                </Modal.Content>
                <Modal.Actions>
                    <Button type='submit' onClick={this.addLook}>Submit</Button>
                </Modal.Actions>
            </Modal>
        )
    }

    addLook = () => {
        let url = 'http://localhost:8080/look',
            payload = {
                description: this.state.description,
                lookTypes: this.state.lookType,
                minTemperature: this.state.minTemp,
                maxTemperature: this.state.maxTemp,
                public: this.state.public,
                userClothAttributesCodes: this.state.cloth,
                picture: null,
            },
            body = JSON.stringify({
                picture: this.state.picture,
                description: this.state.description,
                lookTypes: this.state.lookType,
                minTemperature: this.state.minTemp,
                maxTemperature: this.state.maxTemp,
                public: this.state.public,
                userClothAttributesCodes: this.state.cloth,
            });
        
        axios.post(url, body, {
            headers: {
                'Content-type' : 'application/json',
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    // this.props.addNewLook(payload, response.data);
                    this.setState({})

                }
            })
            .catch(err => console.error(err));
        
        this.closeModal();
    }

    editCheckboxField = (event, data) => {
        this.setState({public: data.checked})
    };

    editClothField = (event, data) => {
        this.setState({cloth: data.value})
    };

    editLookTypeField = (event, data) => {
        let value = data.value.map(item => {
            this.setState(prevState => ({lookType: [...prevState.lookType, {name : item}]}))
        })    
    };

    editField = ({target}) => {
        this.setState({[target.name]: target.value})
    };

    setCommentTime = (time) => {
        let date = new Date(time),
            day = date.getDate(),
            month = date.getMonth(),
            year = date.getFullYear();

            return `${day}.${month}.${year}`
    }

    openModal = () => this.setState({isModalOpen: true});

    closeModal = () => this.setState({isModalOpen: false});

    setCommets = (length, item) => {
        if(length !== 0) {
            return item.comments.map(comment => {
                return(
                    <Comment key={comment.commentId}>
                        <Comment.Avatar src='https://sunriseprowebsites.com/backstage/app/views/client/lutfi-cloud/lutfi-file/images/avatar.png' />
                        <Comment.Content>
                            <Comment.Author as='a'>{comment.login}</Comment.Author>
                            <Comment.Metadata>
                            <div>{this.setCommentTime(comment.lastUpdated)}</div>
                            </Comment.Metadata>
                            <Comment.Text>{comment.message}</Comment.Text>
                            <Comment.Actions>
                            <Comment.Action>Reply</Comment.Action>
                            </Comment.Actions>
                        </Comment.Content>
                    </Comment>
                )
            })
        } else {
            return(
                <Header centered size='large'>No Comments...</Header>
            )   
        }
    }

    setLooks = () => {
        let data = this.props.dataLook;
        return data.map((item, index) => {
            return(
                <div className="look-block" key={index}>
                    <Header as='h2' attached='top'>
                        {item.description}
                    </Header>
                    <Segment attached>
                        <Card.Group itemsPerRow={3}>
                            {this.setClothData(item)}
                            <Card className="lot-add-look">
                                <Icon size="huge" name="plus"/>
                            </Card>
                        </Card.Group>
                        <div className="row space-between">
                            <div onClick={() => this.setLIkeToLook(item.code, item.likes)} className="row align-center">
                                <Icon className="look-icon" color="red" name="like"/>
                                {item.likes}
                            </div>
                            <div onClick={() => this.showComments(index)} className="row align-center">
                                <Icon className="look-icon" color="blue" name="comment"/>
                                {item.comments.length}
                            </div>
                        </div>
                        {
                            this.state.showComments && 
                            this.state.activeComment === index + 1 &&
                            <Comment.Group className="look-comments">
                                <Header as='h3' dividing>
                                Comments
                                </Header>
                                {this.setCommets(item.comments.length, item)}
                            </Comment.Group>
                        }
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
                        Create Your Look
                    </Button>
                </div>
                {this.setLooks()}
                {this.lookModal()}
            </div>
        ) 
    }
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        setLook: setLook,
        setLike: setLike,
        setLookTypes: setLookTypes,
        setClothAttr: setClothAttr,
        addNewLook: addNewLook,
    }, dispatch)
}

function mapStateToProps(state) {
    return {
        dataLook: state.look,
        dataLookType: state.lookTypes,
        dataCloth: state.clothAttr,
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(LookPage);