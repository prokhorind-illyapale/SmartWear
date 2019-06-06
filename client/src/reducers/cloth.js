const initialState = [];

export const cloth = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_CLOTH':
            return [
                ...action.payload
            ];
        case 'ADD_CLOTH':
            return [
                ...state,
                {
                    ...action.payload
                }

            ];
        case 'DELETE_CLOTH':
            return state.filter((name, index) => index !== action.payload);
        default:
            return state;
    }
};