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
                    ...action.payload.clothAttr,
                    code: action.payload.code
                }

            ];
        case 'DELETE_CLOTH_ATTR':
            return state.filter(({code}) => code !== action.payload);
        case 'UPDATE_CLOTH_ATTR':
            return state.map(cloth =>
                cloth.code === action.payload.code
                    ? {
                        ...cloth,
                        ...action.payload.data
                    }
                    : cloth
            );
        default:
            return state;
    }
};