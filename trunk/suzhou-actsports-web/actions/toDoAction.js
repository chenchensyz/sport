/*
 * action 类型
 */
export const FETCH_UPDATE = 'FETCH_UPDATE';
export const DO_VALUE = 'DO_VALUE';
export const CHANGE_MSG = 'CHANGE_MSG';
export const TOGGLE_CHECK = 'TOGGLE_CHECK';
export const UPDATE_MSG ='UPDATE_MSG';
export const UPDATE_NUM ='UPDATE_NUM';
export const UPDATE_ID ='UPDATE_ID';
export const CHECK_TOAST ='CHECK_TOAST';
export const TOGGLE_RADIO ='TOGGLE_RADIO';
export const UPDATE_STATUS = 'UPDATE_STATUS';
export const UPDATE_SESSION_ID = 'UPDATE_SESSION_ID';
export const BUTTON_FLAG = 'BUTTON_FLAG';
/*
 * action 创建函数
 */
export function doValue(id,text) {
    return {type: DO_VALUE,id, text}
}

export function buttonFlag(bool) {
    return {type: BUTTON_FLAG,bool}
}

//更新排名

export function updateNum(num) {
    return {type: UPDATE_NUM,num}
}

 // 改变提示信息
export function changeMsg(title) {
    return {type: CHANGE_MSG, title}
}

export function updateMsg(msg){
	return {type: UPDATE_MSG, msg}
}

 // 选择项目多选
export function toggleCheck(name,id) {
    return {type: TOGGLE_CHECK,name,id}
}

//选择项目单选
export function toggleRadio(name,id) {
    return {type: TOGGLE_RADIO,name,id}
}


 // 选择项目
export function fetchUpdate(list) {
    return {type: FETCH_UPDATE,list}
}

//toast显示消失

export function checkToast(bool) {
    return {type: CHECK_TOAST,bool}
}

//enterID
export function updateId(id) {
    return {type: UPDATE_ID,id}
}
//sessionId
export function updateSessionId(id) {
    return {type: UPDATE_SESSION_ID,id}
}


export function updateStatus(status) {
  return {type: UPDATE_STATUS, status}
}

export function toastShowOff(dispatch,msg) {
    dispatch(checkToast(true));
    dispatch(changeMsg(msg));
    window.setTimeout(function(){
        dispatch(checkToast(false));
    },2000);
}
//postData
export function postData(url,data) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain'
        },
        body: JSON.stringify(data)

    }).then((res) => {
        return res.json()
    }).catch((e) => {
        console.log(e.message)
    });
}

