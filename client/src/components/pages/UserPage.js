import React, { Component } from 'react';
import '../../styleForComponents/AuthPage.css';
import { connect } from 'react-redux';
import {Segment, Header} from 'semantic-ui-react';

    const styleForText ={
        color: 'black'
    };


class UserPage extends Component {

    getRiseHours(){
        if(typeof this.props.data.sys !== 'undefined') {
            let sunrise = this.props.data.sys.sunrise;

            return sunrise.substring(sunrise.indexOf(':') + 1, sunrise.indexOf('.'));
        }
    }

    getSetHours(){
        if(typeof this.props.data.sys !== 'undefined') {
            let sunset = this.props.data.sys.sunset;

            return sunset.substring(sunset.indexOf(':') + 1, sunset.indexOf('.'))

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
                    <p style={styleForText}>Sunset: {this.getRiseHours()} AM</p>
                    <p style={styleForText}>Sunrise: {this.getSetHours()} PM</p>
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


export default connect(mapStateToProps)(UserPage);