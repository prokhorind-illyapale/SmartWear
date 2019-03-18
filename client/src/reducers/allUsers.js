const allUsers = [];

export const usersData = (state = allUsers, action) => {
    switch (action.type) {
        case 'GET_ALL_USERS':
            return [
                ...action.payload
                ];
        case 'DELETE_USER':
            return state.filter(({login}) => login !== action.payload);
        default:
            return state;
    }
};