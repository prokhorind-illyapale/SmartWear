const initialState = [];

export const look = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_LOOK':
            return [
                ...action.payload
            ];
        case 'SET_LIKE':
            return state.map(item => 
                item.likes === action.payload
                ? {
                    ...item,
                    ...action.payload
                }
                : item
            )
        case 'ADD_LOOK':
            return [
                ...state,
                {
                    ...action.payload.look,
                    code: action.payload.code
                }

            ];
        // case 'DELETE_LOOK':
        //     return state.filter((name, index) => index !== action.payload);
        default:
            return state;
    }
};