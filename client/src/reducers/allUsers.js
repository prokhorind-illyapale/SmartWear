const allUsers = [];

export const usersData = (state = allUsers, action) => {
    switch (action.type) {
        case 'GET_ALL_USERS':
            return [
                ...action.payload
                ];
        case 'DELETE_USER':
            return state.filter(({login}) => login !== action.payload);
        case "UPDATE_USER_BY_LOGIN":
            return state.map(user =>
                user.login === action.payload.login
                    ? {
                        ...user,
                        ...action.payload.fields
                    }
                    : user
            );
        default:
            return state;
    }
};