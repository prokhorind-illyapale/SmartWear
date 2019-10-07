const initialState = [];

export const deviceInRoom = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_DEVICE_IN_ROOM':
            return [
                ...action.payload
            ];
        default:
            return state;
    }
};