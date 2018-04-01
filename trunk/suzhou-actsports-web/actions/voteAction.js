/*
 * action 类型
 */
 export const UPDATE_STATUS = 'UPDATE_STATUS';
 export const TOGGLE_VOTE = 'TOGGLE_VOTE';
 export const TOGGLE_ONE = 'TOGGLE_ONE';
 export const DIALOG_MSG = 'DIALOG_MSG';
 export const DIALOG_SHOW = 'DIALOG_SHOW';
 export const HAS_MORE = 'HAS_MORE';
 export const VOTE_UPDATE = 'VOTE_UPDATE';

 //更新状态
 export function updateStatus(status) {
  return {type: UPDATE_STATUS, status}
}
//投票状态(多选)
 export function toggleVote(id) {
  return {type: TOGGLE_VOTE, id}
}

//投票状态(单选)
 export function toggleOne(id) {
  return {type: TOGGLE_ONE, id}
}

export function dialogShowMsg(msg) {
    return {type: DIALOG_MSG, msg}
}

export function dialogShow(bool) {
    return {type: DIALOG_SHOW, bool}
}

export function hasMoreUpdate(bool) {
    return {type: HAS_MORE, bool}
}

export function voteUpdate(list) {
    return {type: VOTE_UPDATE, list}
}

//弹出框显示关闭
export function dialogShowOff(dispatch,msg,bool) {
    dispatch(dialogShowMsg(msg));
    dispatch(dialogShow(bool));
}