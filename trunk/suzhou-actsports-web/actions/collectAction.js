export const TEXT_VALUE = 'TEXT_VALUE';
export const ADD_IMG = 'ADD_IMG';
export const DEL_IMG = 'DEL_IMG';
export const IS_IMG = 'IS_IMG';
export const IS_FILE = 'IS_FILE';
export const IS_COUNT_OVER = 'IS_COUNT_OVER';
export const UPDATE_WX = 'UPDATE_WX';
export const UPDATE_LEN = 'UPDATE_LEN';



export function textValue(text) {
    return {type: TEXT_VALUE,text}
}

export function isImg(bool) {
    return {type: IS_IMG,bool}
}

export function isFile(bool) {
    return {type: IS_FILE,bool}
}

export function addImg(id,src) {
    return {type: ADD_IMG,id,src}
}

export function delImg(id) {
    return {type: DEL_IMG,id}
}

export function isCountOver(bool) {
    return {type: IS_COUNT_OVER,bool}
}

export function updateLen(obj) {
    return {type: UPDATE_LEN,obj}
}