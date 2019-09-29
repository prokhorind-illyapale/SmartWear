export const addNewLook = (look, code) => ({
    type: 'ADD_LOOK',
    payload:  {
        look,
        code
    },
});