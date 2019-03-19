const initialState = {};

export const userWeather = (state = initialState, action) => {
    switch (action.type) {
        case 'GET_WEATHER':
            return {
                ...action.payload
                };
        default:
            return state;
    }
};