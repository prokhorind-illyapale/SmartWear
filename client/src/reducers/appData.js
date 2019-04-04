const initialState = {
    auth: {
        token: typeof window.localStorage.token !== "undefined" ? window.localStorage.token : ""
    },
    userData: {},
};

export const appData = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_TOKEN':
            return {
                ...state,

                auth: {
                        token: action.text
                    }
                 };
        case 'SET_USER_DATA':
            return {
                ...state,
                userData: {
                    ...action.payload
                    }
                };
        case 'LOG_OUT':
            return {
                ...state,
               auth: {
                    token: ''
                }
            };
        case 'UPDATE_USER_PROFILE':
            return {
                ...state,
                userData: {
                    ...action.payload
                }
            };
        default:
            return state;
    }
};