const initialState = [];

export const topThreeLook = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_TOP_THREE_LOOK':
            return [
                ...action.payload
            ];
        default:
            return state;
    }
};