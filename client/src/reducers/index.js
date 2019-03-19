import { combineReducers } from "redux";
import { appData } from "./appData";
import { usersData } from "./allUsers";
import { userWeather } from "./userWeather";

const allReducers = combineReducers({
    appData,
    usersData,
    userWeather
});

const reducers = (state, action) => {
    return allReducers(state, action);
};

export default reducers;