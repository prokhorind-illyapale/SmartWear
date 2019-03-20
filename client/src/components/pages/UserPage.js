import React, { Component } from 'react';
import '../../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header} from 'semantic-ui-react';
import axios from "axios";
import {bindActionCreators} from "redux";
import {getWeather} from "../../actions/getWeather";

    const styleForText ={
        color: 'black'
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

            return sunrise.substring(sunrise.indexOf('T') + 1, sunrise.indexOf('.'));
        }
    }

    getSetHours(){
        if(typeof this.props.data.sys !== 'undefined') {
            let sunset = this.props.data.sys.sunset;

            return sunset.substring(sunset.indexOf('T') + 1, sunset.indexOf('.'))

        }
    }


    render() {
        let data = this.props.data;

        return (
            <div>
                <Segment className="auth-container">
                    <Header textAlign='center'>
                        {data.name}
                    </Header>
                    <p style={styleForText}>Now: {typeof data.main !== 'undefined' && data.main.temp}&deg;</p>
                    <p style={styleForText}>Max: {typeof data.main !== 'undefined' && data.main.temp_max}&deg;</p>
                    <p style={styleForText}>Min: {typeof data.main !== 'undefined' && data.main.temp_min}&deg;</p>
                    <p style={styleForText}>Sunrise: {this.getRiseHours()} PM</p>
                    <p style={styleForText}>Sunset: {this.getSetHours()} AM</p>
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