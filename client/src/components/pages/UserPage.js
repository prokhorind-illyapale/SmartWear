import React, { Component } from 'react';
import '../../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header,  Divider} from 'semantic-ui-react';
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


class UserPage extends Component {

    componentDidMount() {
        let url_weather = 'http://localhost:8080/user/get/weather';

        axios.get(url_weather, {
            headers: {
                'Authorization': "Basic " + window.localStorage.token,
                'Content-type' : 'application/json',
            }
        })
            .then(responseWeather => {
                this.props.getWeather(responseWeather.data)
            })
            .catch(err => console.log(err));
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

    getRoundTemp() {
        if(typeof this.props.data.main !== 'undefined') {
            let temp = Number(this.props.data.main.temp);

            return Math.round(temp).toString();
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


    render() {
        let data = this.props.data;

        return (
            <div>
                <Segment className="auth-container">
                    <Header as='h1' textAlign='center'>
                        {data.name} <i className={this.getIconWeather()}/>
                    </Header>
                    <Header as='h2' textAlign='center'>
                        {this.getRoundTemp()}&deg;
                    </Header>
                    <Divider/>
                    <div style={flexField}>
                        <p style={styleForText}><b>Min:</b> {typeof data.main !== 'undefined' && data.main.temp_min}&deg;</p>
                        <p style={styleForText}><b>Max:</b> {typeof data.main !== 'undefined' && data.main.temp_max}&deg;</p>
                    </div>
                    <Divider/>
                    <div style={flexField}>
                        <p style={styleForText}><b>Sunrise:</b> {this.getRiseHours()}</p>
                        <p style={styleForText}><b>Sunset:</b> {this.getSetHours()}</p>
                    </div>

                </Segment>
            </div>
        )

    }

}

function mapStateToProps(state) {
    return {
        data: state.userWeather
    }
}
function matchDispatchToProps(dispath) {
    return bindActionCreators(
        {
            getWeather: getWeather
        }, dispath);
}


export default connect(mapStateToProps, matchDispatchToProps)(UserPage);