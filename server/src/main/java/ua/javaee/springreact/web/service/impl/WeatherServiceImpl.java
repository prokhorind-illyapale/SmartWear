package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.data.weatherapi.Climate;
import ua.javaee.springreact.web.repository.WeatherRepository;
import ua.javaee.springreact.web.service.WeatherService;

/**
 * Created by kleba on 16.03.2019.
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public Climate getClimateByCityName(String city) {
        return weatherRepository.getClimateByCityName(city);
    }
}
