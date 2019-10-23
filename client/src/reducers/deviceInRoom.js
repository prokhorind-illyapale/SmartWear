const initialState = [];

export const deviceInRoom = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_DEVICE_IN_ROOM':
            return [
                ...action.payload
            ];
        case 'ADD_DEVICE_IN_ROOM':
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