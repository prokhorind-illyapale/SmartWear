import React, { Component } from 'react';
import '../../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header,  Divider, Button, Message} from 'semantic-ui-react';
import axios from "axios";
import {bindActionCreators} from "redux";
import {getWeather} from "../../actions/getWeather";
import {weatherIcons} from '../../img/weatherIcons';


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
            this.props.getWeather(responseWeather.data);
            this.setState(responseWeather.data.main)
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


    render() {
        let data = this.props.data;

        if(Object.keys(data).length !== 0) {
            return (
                <div>
                    <Segment className="auth-container">
                        <Button.Group style={degBtn}>
                            <Button active={this.state.celsius} onClick={this.changeToCelsius}>&deg;C</Button>
                            <Button active={this.state.fahrenheit} onClick={this.changeToFahrenheit}>&deg;F</Button>
                        </Button.Group>
                        <Header as='h1' textAlign='center'>
                            {data.name} <i className={this.getIconWeather()}/><br/>
                            <span style={smallText}>{typeof data.weather !== 'undefined' && data.weather.map(data => data.description)}</span>
                        </Header>
                        <Header as='h2' textAlign='center'>
                            {(Math.round(this.state.temp)).toString()}&deg;
                        </Header>
                        <Divider/>
                        <div style={flexField}>
                            <p style={styleForText}><b>Min:</b> {(Math.round(this.state.temp_min)).toString()}&deg;</p>
                            <p style={styleForText}><b>Max:</b> {(Math.round(this.state.temp_max)).toString()}&deg;</p>
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
        userData: state.appData.userData
    }
}
function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            getWeather: getWeather
        }, dispath);
}


export default connect(mapStateToProps, matchDispatchToProps)(UserPage);