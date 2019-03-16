package ua.javaee.springreact.web.repository;

import ua.javaee.springreact.web.data.weatherapi.Climate;

/**
 * Created by kleba on 16.03.2019.
 */
public interface WeatherRepository {

    Climate getClimateByCityName(String city);
}
