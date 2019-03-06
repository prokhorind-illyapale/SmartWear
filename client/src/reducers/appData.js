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
        default:
            return state;
    }
};