export const updateUserByLogin = (login, fields) => ({
    type: 'UPDATE_USER_BY_LOGIN',
    payload: {
        login,
        fields
    }
});