package ua.javaee.springreact.web.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.javaee.springreact.web.data.weatherapi.Climate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * Created by kleba on 16.03.2019.
 */
@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

    private Logger logger = LoggerFactory.getLogger(WeatherRepositoryImpl.class);
    private static final String WEATHER_PROPERTIES = "weather.properties";

    @Override
    public Climate getClimateByCityName(String city) {
        ObjectMapper objectMapper = new ObjectMapper();
        Climate climate = null;
        URL obj = null;
        try {
            obj = new URL(getConnectionUrl(city));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            String weatherLine = getAnswer(con);
            climate = objectMapper.readValue(weatherLine, Climate.class);
        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
        return climate;
    }

    private String getAnswer(HttpURLConnection con) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sbJson = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sbJson.append(line + "\n");
        }
        br.close();
        return sbJson.toString();
    }

    private String getConnectionUrl(String city) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://api.openweathermap.org/data/2.5/weather?");
        sb.append("units=metric");
        sb.append("&");
        sb.append("q=");
        sb.append(city);
        sb.append("&");
        sb.append("appid=");
        sb.append(getKey());
        return sb.toString();
    }

    private String getKey() {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            prop.load(is = getClass().getClassLoader().getResourceAsStream(WEATHER_PROPERTIES));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return prop.getProperty("key");
        }
    }
}
