const initialState = [];

export const clothTypes = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_CLOTH_TYPES':
            return [
                ...action.payload
            ];
        case 'ADD_CLOTH_TYPE':
            return [
                ...state,
                {
                    ...action.payload
                }
                
            ];
        case 'DELETE_CLOTH_TYPE':
            return state.filter(({name}) => name !== action.payload);
        default:
            return state;
    }
};