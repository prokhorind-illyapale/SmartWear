const initialState = [];

export const room = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_ROOM':
            return [
                ...action.payload
            ];
        case 'ADD_ROOM':
            return [
                ...state,
                {
                    ...action.payload
                }

            ];
        case 'DELETE_ROOM':
            return state.filter(({roomName}) => roomName !== action.payload);
        default:
            return state;
    }
};