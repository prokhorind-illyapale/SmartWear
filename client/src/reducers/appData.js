const initialState = [];

export const appData = (state = initialState, action) => {
    switch (action.type) {
        case 'CHANGE_LOGGED_IN':
            return [
                ...state,
                {
                    ...action.payload
                }
            ];
        case 'SET_TOKEN':
            return [
                ...state,
                 action.text

            ];
        default:
            return state;
    }
};