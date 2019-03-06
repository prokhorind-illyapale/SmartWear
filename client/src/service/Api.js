import axios from 'axios';

const axiosConfig = {
    baseURL: 'http://localhost:8080/',
    timeout: 30000,
};

const Api = axios.create(axiosConfig);

export default Api;
