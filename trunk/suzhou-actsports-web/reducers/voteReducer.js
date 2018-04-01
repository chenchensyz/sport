import {FETCH_UPDATE,UPDATE_NUM} from '../actions/toDoAction';
import {TOGGLE_VOTE,TOGGLE_ONE,DIALOG_MSG,DIALOG_SHOW,HAS_MORE,VOTE_UPDATE} from '../actions/voteAction';


export function voteLimit(state=2,action){
  switch (action.type) {
        case UPDATE_NUM:
        return action.num;
        break;
        default:
        return state;
    }
    return state;
}

export function dialogMsg(state='',action){
  switch (action.type) {
        case DIALOG_MSG:
        return action.msg;
        break;
        default:
        return state;
    }
    return state;
}
export function dialogShow(state='',action){
  switch (action.type) {
        case DIALOG_SHOW:
        return action.bool;
        break;
        default:
        return state;
    }
    return state;
}

export function hasMore(state=true,action){
  switch (action.type) {
        case HAS_MORE:
        return action.bool;
        break;
        default:
        return state;
    }
    return state;
}

export function voteChooseList(state = [{
          "id": "402882f0591ade2001591ade28f00005",
          "name": "投票1",
          "count": "0",
          "orderNum": "1",
          "remark": "投票投票投票投票投票投票投票投票投票投票投票投票",
          "checked":true,
          "img": ""
        },{
          "id": "402882f0591ade2001591ade28f00000",
          "name": "投票1",
          "count": "0",
          "orderNum": "1",
          "remark": "投票投票投票投票投票投票投票投票投票投票投票投票",
          "checked":true,
          "img": ""
        }], action) {

    let tempState=[];
    switch (action.type) {
        case VOTE_UPDATE:
        return action.list;
        break;
        case TOGGLE_VOTE:
            tempState = state.map((list, index) => {
                if (list.id === action.id) {
                    return Object.assign({}, list, {
                        checked: !list.checked
                    });
                }
                return list
            });
        break;
        case TOGGLE_ONE:
          tempState = state.map((list, index) => {
                if (list.id === action.id) {
                    return Object.assign({}, list, {
                        checked: true
                    });
                }else{
                  return Object.assign({}, list, {
                        checked: false
                    });
                }
                return list
            });
        break;
        default:
            tempState = state
    }
    return tempState;
}
