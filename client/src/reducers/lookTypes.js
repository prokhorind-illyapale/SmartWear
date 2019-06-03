const initialState = [];

export const lookTypes = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_LOOK_TYPES':
            return [
                ...action.payload
            ];
        case 'ADD_LOOK_TYPE':
            return [
                ...state,
                {
                    ...action.payload
                }

            ];
        case 'DELETE_LOOK_TYPE':
            return state.filter(({name}) => name !== action.payload);
        default:
            return state;
    }
};