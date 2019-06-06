export const addClothAttr = (clothAttr, code) => ({
    type: 'ADD_CLOTH_ATTR',
    payload:  {
        clothAttr,
        code
    },
});