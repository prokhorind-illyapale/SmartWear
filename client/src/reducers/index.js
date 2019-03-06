import { combineReducers } from "redux";
import { appData } from "./appData";

const allReducers = combineReducers({
    appData
});

const reducers = (state, action) => {
    return allReducers(state, action);
};

export default reducers;