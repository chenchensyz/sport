import {TEXT_VALUE,ADD_IMG,DEL_IMG,IS_IMG,IS_FILE,IS_COUNT_OVER,UPDATE_WX,UPDATE_LEN} from '../actions/collectAction';
import {UPDATE_NUM} from '../actions/toDoAction';


export function uploadImg(state=[],action){
	let tempState;
	switch (action.type) {
        case ADD_IMG:        
        tempState=[
        	 ...state, {
                    id: action.id,
                    src:action.src
                }
        ];       
        break;
        case DEL_IMG:
        tempState=state.filter((item)=>{
        	return item.id != action.id;
        });
        break;
        default:
        tempState = state;
    }
	return tempState;
}

export function isImgShow(state=false,action){
  switch (action.type) {
        case IS_IMG:
        return action.bool;
        break;
        default:
        return state;
    }
    return state;
}

export function isFileShow(state=false,action){
  switch (action.type) {
        case IS_FILE:
        return action.bool;
        break;
        default:
        return state;
    }
    return state;
}





export function areaValue(state='',action){
  switch (action.type) {
        case TEXT_VALUE:
        return action.text;
        break;
        default:
        return state;
    }
    return state;
}

export function textCount(state=0,action){
  switch (action.type) {
        case UPDATE_NUM:
        return action.num;
        break;
        default:
        return state;
    }
    return state;
}

export function isOver(state=false,action){
  switch (action.type) {
        case IS_COUNT_OVER:
        return action.bool;
        break;
        default:
        return state;
    }
    return state;
}

//微信配置项
export function weixinConfig(state=null,action){
  switch (action.type) {
        case UPDATE_WX:
        return action.obj;
        break;
        default:
        return state;
    }
    return state;
}

//意见反馈内容长度
// export function textLen(state=null,action){
//     switch (action.type) {
//         case UPDATE_LEN:
//         return action.obj;
//         break;
//         default:
//         return state;
//     }
//     return state;
// }
export function textMax(state=0,action){
    switch (action.type) {
        case 'UPDATE_MAX':
        return action.num;
        break;
        default:
        return state;
    }
    return state;
}
export function textMin(state=0,action){
    switch (action.type) {
        case 'UPDATE_MIN':
        return action.num;
        break;
        default:
        return state;
    }
    return state;
}
