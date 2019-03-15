const initialState = {
    auth: {
        token: typeof window.localStorage.token !== "undefined" ? window.localStorage.token : ""
    },
    userData: {},
    allUsers: [],
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
        case 'GET_ALL_USERS':
            return {
                ...state,
                allUsers: [
                    ...action.payload
                ]
            };
        default:
            return state;
    }
};