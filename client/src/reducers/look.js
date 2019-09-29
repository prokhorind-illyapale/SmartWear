const initialState = [];

export const look = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_LOOK':
            return [
                ...action.payload
            ];
        // case 'ADD_LOOK':
        //     return [
        //         ...state,
        //         {
        //             ...action.payload
        //         }

        //     ];
        // case 'DELETE_LOOK':
        //     return state.filter((name, index) => index !== action.payload);
        default:
            return state;
    }
};