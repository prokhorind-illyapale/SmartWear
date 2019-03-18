import { combineReducers } from "redux";
import { appData } from "./appData";
import { usersData } from "./allUsers";

const allReducers = combineReducers({
    appData,
    usersData
});

const reducers = (state, action) => {
    return allReducers(state, action);
};

export default reducers;