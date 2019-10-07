import { combineReducers } from "redux";
import { appData } from "./appData";
import { usersData } from "./allUsers";
import { userWeather } from "./userWeather";
import { clothTypes } from "./clothTypes";
import { lookTypes } from "./lookTypes";
import { cloth } from "./cloth";
import { clothAttr } from "./clothAttr";
import { look } from "./look";
import { topThreeLook } from "./topThreeLook";
import { commandList } from "./commandList";
import { devices } from "./devices"; 
import { room } from "./room";
import { deviceInRoom } from "./deviceInRoom"

const allReducers = combineReducers({
    appData,
    usersData,
    userWeather,
    clothTypes,
    lookTypes,
    cloth,
    clothAttr,
    look,
    topThreeLook,
    commandList,
    devices,
    room,
    deviceInRoom
});

const reducers = (state, action) => {
    return allReducers(state, action);
};

export default reducers;