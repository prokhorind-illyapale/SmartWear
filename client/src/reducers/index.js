import { combineReducers } from "redux";
import { appData } from "./appData";
import { usersData } from "./allUsers";
import { userWeather } from "./userWeather";
import { clothTypes } from "./clothTypes";

const allReducers = combineReducers({
    appData,
    usersData,
    userWeather,
    clothTypes
});

const reducers = (state, action) => {
    return allReducers(state, action);
};

export default reducers;