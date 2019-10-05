const initialState = [];

export const commandList = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_COMMAND_LIST':
            return [
                ...action.payload
            ];
        case 'ADD_COMMAND':
            return [
                ...state,
                {
                    ...action.payload
                }
                
            ];
        case 'DELETE_COMMAND':
            return state.filter(({name}) => name !== action.payload);
        default:
            return state;
    }
};