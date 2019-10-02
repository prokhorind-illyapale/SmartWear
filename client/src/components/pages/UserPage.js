import React, { Component } from 'react';
import '../../styleForComponents/AuthPage.css';
import '../../styleForComponents/Look.css';
import '../../styleForComponents/kit.css';
import {setLike} from "../../actions/setLike";
import { connect } from 'react-redux';
import {Segment, Header,  Divider, Button, Message, Loader, Icon, Card, Comment, Image} from 'semantic-ui-react';
import axios from "axios";
import {bindActionCreators} from "redux";
import {getWeather} from "../../actions/getWeather";
import { setTopThreeLook } from "../../actions/setTopThreeLook";
import {weatherIcons} from '../../img/weatherIcons';
import {clothIcons} from '../../img/clothIcons';
import {setClothAttr} from "../../actions/setClothAttr";


    const styleForText ={
        color: 'black',
        fontSize: '15px'
    };

    const flexField = {
        display: 'flex',
        justifyContent: 'space-between',
        padding: '5px 15px',
    };

    const smallText = {
        fontSize: '13px'
    };

    const degBtn = {
        position: 'absolute',
        top: '10px',
        right: '10px'
    };


class UserPage extends Component {

    state = {
        celsius : true,
        fahrenheit: false,
        showComments: false,
        activeComment: 0,
    };

    componentDidMount() {
        let url_weather = 'http://localhost:8080/user/get/weather';

        axios.get(url_weather, {
            timeout: 60000,
            headers: {
                'Authorization': "Basic " + window.localStorage.token,
                'Content-type' : 'application/json',
            }
        })
        .then(responseWeather => {
            if(responseWeather.status === 200) {
                this.props.getWeather(responseWeather.data);
                this.setState(responseWeather.data.main);

                let minTemp = Math.round(this.state.temp_min),
                    maxTemp = Math.round(this.state.temp_max);

                let topThreeUrl = `http://localhost:8080/look/top?minTemp=${minTemp}&maxTemp=${maxTemp}`
                axios.get(topThreeUrl, {
                    headers: {
                        'Authorization': "Basic " + window.localStorage.token,
                        'Content-type' : 'application/json',
                    }
                })
                    .then(response => {
                        if(response.status === 200) {
                            this.props.setTopThreeLook(response.data)
                        }
                    })
                    .catch(err => console.error(err));
            }
        })
        .catch(err => console.log(err))

    }

    getRiseHours(){
        if(typeof this.props.data.sys !== 'undefined') {
            let sunrise = this.props.data.sys.sunrise;

            return sunrise.substring(sunrise.indexOf('T') + 1, sunrise.indexOf(':') + 3);
        }
    }

    getSetHours(){
        if(typeof this.props.data.sys !== 'undefined') {
            let sunset = this.props.data.sys.sunset;

            return sunset.substring(sunset.indexOf('T') + 1, sunset.indexOf(':') + 3)

        }
    }

    getIconWeather() {
        if(typeof this.props.data.weather !== 'undefined') {
            let weather = this.props.data.weather,
                prefix = 'wi wi-',
                code = weather[0].id,
                icon = weatherIcons[code].icon;

            if (!(code > 699 && code < 800) && !(code > 899 && code < 1000)) {
                icon = 'day-' + icon;
            }

           return prefix + icon;

        }

    }

    changeToCelsius = () => {
        if(this.state.celsius === false) {
            this.setState({
                ...this.state,
                celsius: true,
                fahrenheit: false,
                temp: ( Number(this.state.temp) - 32) * 5/9,
                temp_max: ( Number(this.state.temp_max) - 32) * 5/9,
                temp_min: ( Number(this.state.temp_min) - 32) * 5/9
            });
        }


    };

    changeToFahrenheit = () => {
        if(this.state.fahrenheit === false) {
            this.setState({
                ...this.state,
                celsius: false,
                fahrenheit: true,
                temp: ( Number(this.state.temp) * 9/5) + 32,
                temp_max: ( Number(this.state.temp_max) * 9/5) + 32,
                temp_min: ( Number(this.state.temp_min) * 9/5) + 32
            });
        }


    };

    roundTemp = (temp) => {
        let round = Math.round(temp);

        return round.toString();
    };

    showComments = (index) => {
        this.state.showComments === false ? this.setState({showComments: true, activeComment: index + 1}) : this.setState({showComments: false, activeComment: 0})
    }

    setCommentTime = (time) => {
        let date = new Date(time),
            day = date.getDate(),
            month = date.getMonth(),
            year = date.getFullYear();

            return `${day}.${month}.${year}`
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
        let data = this.props.topThreeData;
        return data.map((item, index) => {
            return(
                <div className="look-block" key={index}>
                    <Header as='h2' attached='top'>
                        {item.description}
                    </Header>
                    <Segment  attached>
                        <Card.Group itemsPerRow={3}>
                            {this.setClothData(item)}
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
        let data = this.props.data;

        if(Object.keys(data).length !== 0) {
            return (
                <div className="main-page__container column centered">
                    <div className="main-page--wheather__container">
                        {/* <Header className="white" as='h2' icon textAlign='center'>
                            <Icon name='bolt' circular />
                            <Header.Content>Wheather</Header.Content>
                        </Header> */}
                        <Segment className="main-page--temp__container">
                            <Button.Group style={degBtn}>
                                <Button active={this.state.celsius} onClick={this.changeToCelsius}>&deg;C</Button>
                                <Button active={this.state.fahrenheit} onClick={this.changeToFahrenheit}>&deg;F</Button>
                            </Button.Group>
                            <Header as='h1' textAlign='center'>
                                {data.name} <i className={this.getIconWeather()}/><br/>
                                <span style={smallText}>{typeof data.weather !== 'undefined' && data.weather.map(data => data.description)}</span>
                            </Header>
                            <Header as='h2' textAlign='center'>
                                {isNaN(this.roundTemp(this.state.temp)) ? <Loader size='small' active/> : this.roundTemp(this.state.temp)}&deg;
                            </Header>
                            <Divider/>
                            <div style={flexField}>
                                <p style={styleForText}><b>Min:</b> {isNaN(this.roundTemp(this.state.temp_min)) ? '' : this.roundTemp(this.state.temp_min)}&deg;</p>
                                <p style={styleForText}><b>Max:</b> {isNaN(this.roundTemp(this.state.temp_max)) ? '' : this.roundTemp(this.state.temp_max)}&deg;</p>
                            </div>
                            <Divider/>
                            <div style={flexField}>
                                <p style={styleForText}><b>Sunrise:</b> {this.getRiseHours()}</p>
                                <p style={styleForText}><b>Sunset:</b> {this.getSetHours()}</p>
                            </div>
                            <Divider/>
                            <div style={flexField}>
                                <p style={styleForText}><b>Humidity:</b> {typeof data.main !== 'undefined' && data.main.humidity}%</p>
                                <p style={styleForText}><b>Pressure:</b> {typeof data.main !== 'undefined' && data.main.pressure} hPa</p>
                            </div>
                            <Divider/>
                            <div style={flexField}>
                                <p style={styleForText}><b>Wind Speed:</b> {typeof data.wind !== 'undefined' && data.wind.speed} meter/sec</p>
                                <p style={styleForText}><b>Wind Degrees:</b> {typeof data.wind !== 'undefined' && data.wind.deg}&deg;</p>
                            </div>
                        </Segment> 
                    </div>
                    <div className="main-page--look__container">
                    {/* <Header className="white" as='h2' icon textAlign='center'>
                        <Icon name='chess queen' circular />
                        <Header.Content>Top 3</Header.Content>
                    </Header> */}
                        {this.setLooks()}
                    </div>
                </div>
            )
        } else {
            let city = this.props.userData.city;
            return (
                    <Message warning className="auth-container">
                        {city !== '' && <p style={styleForText}>We can't find city with name <b>{city}</b></p>}
                        {city === '' && <p style={styleForText}>Please, choose your city</p>}
                    </Message>
                )
        }

    }

}

function mapStateToProps(state) {
    return {
        data: state.userWeather,
        userData: state.appData.userData,
        topThreeData: state.topThreeLook,
    }
}
function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            getWeather: getWeather,
            setTopThreeLook: setTopThreeLook,
            setClothAttr: setClothAttr,
            setLike: setLike,
        }, dispath);
}


export default connect(mapStateToProps, matchDispatchToProps)(UserPage);