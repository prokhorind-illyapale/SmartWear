const initialState = [];


export const clothAttr = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_CLOTH_ATTR':
            return [
                ...action.payload
            ];
        case 'ADD_CLOTH_ATTR':
            return [
                ...state,
                {
                    ...action.payload,
                    code: state.length !== 0 ? state[state.length - 1].code + 1 : 1
                }

            ];
        case 'DELETE_CLOTH_ATTR':
            return state.filter(({code}) => code !== action.payload);
        default:
            return state;
    }
};