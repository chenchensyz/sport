import {UPDATE_SESSION_ID,DO_VALUE,CHANGE_MSG,CHECK_TOAST,UPDATE_ID,FETCH_UPDATE,UPDATE_MSG,UPDATE_NUM,TOGGLE_RADIO,TOGGLE_CHECK, UPDATE_STATUS,BUTTON_FLAG} from '../actions/toDoAction';


export function status(state = "", action) {
    switch (action.type) {
        case UPDATE_STATUS:
        return action.status;
        break;
        default:
        return state;
    }
    return state;
}

export function buttonToggle(state = false, action) {
    switch (action.type) {
        case BUTTON_FLAG:
        return action.bool;
        break;
        default:
        return state;
    }
    return state;
}

export function currentNum(state = 0, action) {
    switch (action.type) {
        case UPDATE_NUM:
        return action.num;
        break;
        default:
        return state;
    }
    return state;
}
export function showToasts(state = true, action){
    switch (action.type) {
        case CHECK_TOAST:
        return action.bool;
        break;
        default:
        return state;
    }
    return state;
}

export function message(state = "", action) {
    switch (action.type) {
        case UPDATE_MSG:
        return action.msg;
        break;
        default:
        return state;
    }
    return state;
}

export function enterID(state = "", action){
    switch (action.type) {
        case UPDATE_ID:
        return action.id;
        break;
        default:
        return state;
    }
    return state;
}

export function sessionID(state = "", action){
    switch (action.type) {
        case UPDATE_SESSION_ID:
        return action.id;
        break;
        default:
        return state;
    }
    return state;
}

export function validateMsg(state = "", action) {
    switch (action.type) {
        case CHANGE_MSG:
        return action.title;
        break;
        default:
        return state;
    }
    return state;
}
// [
//         {
//             "id":uuid.v1(),
//             "title":"姓名",
//             "type":"text",
//             "value":"",
//             "rules":[
//                 "require"
//             ]
//         },
//         {
//             "id":uuid.v1(),
//             "title":"手机",
//             "type":"number",
//             "value":"",
//             "rules":[
//                 "require",
//                 "Phone"
//             ]
//         },
//         // {
//         //     "id":uuid.v1(),
//         //     "title":"身份证",
//         //     "type":"text",
//         //     "value":"",
//         //     "rules":[
//         //         "require",
//         //         "card"
//         //     ]
//         // },
//         {
//             "id":uuid.v1(),
//             "title":"报名项目",
//             "type":"checkbox",
//             "values":"",
//             "chooseList":[
//                 {
//                     "id":"choose1",
//                     "text":"广州",
//                     "executable":true
//                 },
//                 {
//                     "id":"choose2",
//                     "text":"深圳",
//                     "executable":true
//                 },
//                 {
//                     "id":"choose3",
//                     "text":"珠海",
//                     "executable":false
//                 }
//             ],
//             "rules":[],
//             "chooseLimit":"4"
//         }]
export function formList(state = [
    {
        "id":"3423423",
      "title": "姓名",
      "type": "text",
      "rules": [
        "required",
        "0-32"
      ],
      "value":"",
      "entryChooseList": null,
      "voteChooseList": null,
      "chooseLimit": null
    },
    {
         "id":"345343",
      "title": "手机号11",
      "type": "text",
      "rules": [
        "required",
        "phone",
        "0-32"
      ],
      "value":"",
      "entryChooseList": null,
      "voteChooseList": null,
      "chooseLimit": null
    }
    ], action) {

    let tempState;
    switch (action.type) {
        case FETCH_UPDATE:
        return action.list;
        break;
        case DO_VALUE:
        tempState = state.map((list, index) => {
                if(list.id===action.id){
                    // return Object.assign({}, list, {
                    //     value: action.text
                    // });
                    list.value=action.text;
                }
            return list;
            });
        break;
        case TOGGLE_CHECK:
            tempState = state;
            tempState = state.map((list, index) => {
                if(list.id===action.name){
                        let items = list.chooseList.map((item,itemIndex)=>{
                        if(item.id===action.id){
                            item.checked = !item.checked;
                        }
                    })
                }
                return list;
            });
        break;
        case TOGGLE_RADIO:
        tempState = state;
            tempState = state.map((list, index) => {
                if(list.id===action.name){
                        let items = list.chooseList.map((item,itemIndex)=>{
                        if(item.id===action.id){
                            item.checked = true;
                        }else{
                            item.checked =false;
                        }
                    })
                }
                return list;
            });
        break;
        default:
            tempState = state
    }
    return tempState;
}


