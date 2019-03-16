package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.data.weatherapi.Climate;

/**
 * Created by kleba on 16.03.2019.
 */
public interface WeatherService {
    Climate getClimateByCityName(String city);
}
