import React,  {Component} from "react";
import {Button, Card, Dropdown, Icon, Image, Modal, Form, Input, Confirm, Header, Segment, Comment} from 'semantic-ui-react';
import connect from "react-redux/es/connect/connect";
import axios from "axios";
import {bindActionCreators} from "redux";
import {setLook} from "../../../actions/setLook";
import '../../../styleForComponents/Look.css';
import '../../../styleForComponents/kit.css';
import {clothIcons} from '../../../img/clothIcons';

class LookPage extends Component {

    state = {
        showComments: false,
        activeComment: 0
    }

    componentDidMount() {
        let url = 'http://localhost:8080/look/get/all/',
            str = atob(window.localStorage.token),
            login = str.substring(0 ,str.indexOf(':'));
    
        axios.get(url + login, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token
            }
        })
            .then(response => {
                if(response.status === 200) {
                    console.log(response.data);
                    this.props.setLook(response.data);
                }
            })
            .catch(err => console.error(err));
    }

    showComments = (index) => {
        this.state.showComments === false ? this.setState({showComments: true, activeComment: index + 1}) : this.setState({showComments: false, activeComment: 0})
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

    setCommets = (length, item) => {
        if(length !== 0) {
            return item.comments.map(comment => {
                return(
                    <Comment key={comment.commentId}>
                        <Comment.Avatar src='https://sunriseprowebsites.com/backstage/app/views/client/lutfi-cloud/lutfi-file/images/avatar.png' />
                        <Comment.Content>
                            <Comment.Author as='a'>{comment.login}</Comment.Author>
                            <Comment.Metadata>
                            <div>{comment.lastUpdated}</div>
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
                <Header centered size='large'>No Comment...</Header>
            )   
        }
    }

    render() {
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
                            <div className="row align-center">
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
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        setLook: setLook,
    }, dispatch)
}

function mapStateToProps(state) {
    return {
        dataLook: state.look
    }
}

export default connect(mapStateToProps, matchDispatchToProps)(LookPage);