const initialState = [];

export const devices = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_DEVICES':
            return [
                ...action.payload
            ];
        case 'ADD_DEVICE':
            return [
                ...state,
                {
                    ...action.payload
                }

            ];
        case 'DELETE_DEVICE':
            return state.filter((name, index) => index !== action.payload);
        default:
            return state;
    }
};