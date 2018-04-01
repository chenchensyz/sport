import React from 'react';
import {render} from 'react-dom';
import {Provider} from 'react-redux';
import {createStore} from 'redux';
import {IndexRoute, Route, Router, browserHistory, hashHistory} from 'react-router';
import 'whatwg-fetch';
import {fetchUpdate, updateNum, updateMsg, updateId, updateStatus,postData,updateSessionId} from './actions/toDoAction';
import {voteUpdate} from './actions/voteAction';
import {isImg,isFile} from './actions/collectAction';
import acApp from './reducers/index';
import uuid from 'node-uuid';
import configPath from './config/dev';
import 'babel-polyfill';
const store = createStore(acApp);
console.log(store.getState())
var sessionIds='?sessionId';

const goWhichSign = (nextState, replace) => {
    document.title='报名活动';
    let sessionId=nextState.location.query.sessionId||nextState.location.query[sessionIds], url='';
    if(sessionId){
        url=configPath.path + 'signUp/getSignUpPage?sessionId='+sessionId;
        store.dispatch(updateSessionId(sessionId));
    }else{
        url=configPath.path + 'signUp/getSignUpPage';
    }
    if (!!nextState.params.id) {
        postData(url,{"entryId": nextState.params.id}).then((data) => {
            console.log(data);
            if (data == '50011') {
                window.location.href = configPath.path + 'wechatAuth?callbackUrl=' + encodeURIComponent(window.location.href);
                //return;
            }
            store.dispatch(updateStatus(data.status));
            switch (data.status) {
                case "success":
                    store.dispatch(updateNum(data.currentNum));
                    break;
                case "end":
                    store.dispatch(updateMsg(data.message));
                    break;
                case "unStart":
                  break;
                case "doing":
                    let formList = data.actFormListPojo.map(function(item, index) {
                        item.id = 'formList' + index;
                        item.chooseList = item.entryChooseList;
                        item.value = '';
                        return item;
                    });
                    store.dispatch(fetchUpdate(formList));
                    store.dispatch(updateId(nextState.params.id));
            }
        });
    }
}

const goWhichVote = (nextState, replace) => {
    console.log(nextState)
    document.title='投票活动';
    let dataJson = {"voteId": nextState.params.id,"pageNum":1,"pageSize":12};
    let sessionId=nextState.location.query.sessionId||nextState.location.query[sessionIds], url='';
    if(sessionId){
        url=configPath.path + 'vote/getVotePage?sessionId='+sessionId;
        store.dispatch(updateSessionId(sessionId));
    }else{
        url=configPath.path + 'vote/getVotePage';
    }
    if (!!nextState.params.id) {
        postData(url,dataJson).then((data) => {
            if (data == '50011') {
                window.location.href = configPath.path + 'wechatAuth?callbackUrl=' + encodeURIComponent(window.location.href);
                //return;
            }
            store.dispatch(updateStatus(data.status));
            switch (data.status) {
                case "success":
                    store.dispatch(updateMsg(data.message));
                    break;
                case "end":
                    store.dispatch(updateMsg(data.message));
                    break;
                case "unStart":
                  break;
                case "doing":
                    store.dispatch(updateMsg(data.message));
                    //投票列表数据
                    let actFormListPojo = data.actFormListPojo.filter(function (item) {
                        return item.type=='list';
                    });
                    store.dispatch(updateNum(actFormListPojo[0].chooseLimit));
                    let voteList = actFormListPojo[0].voteChooseList.map(function(item, index) {
                        item.value = '';
                        return item;
                    });

                    //表单数据
                    let actFormList = data.actFormListPojo.filter(function (item) {
                        return item.type!='list';
                    });
                    let formList = actFormList.map(function(item, index) {
                        item.id = uuid.v1();;
                        item.value = '';
                        return item;
                    });
                    console.log(formList)

                    store.dispatch(fetchUpdate(formList));
                    store.dispatch(voteUpdate(voteList));
                    store.dispatch(updateId(nextState.params.id));
            }
        });
    }
}

const goWhichCollect = (nextState, replace)=>{
    document.title='征集信息';
    if (!!nextState.params.id) {
        let sessionId=nextState.location.query.sessionId||nextState.location.query[sessionIds], url='';
        let dataJson = {"collectId": nextState.params.id,"url":encodeURIComponent(window.location.href.split('#')[0])};
        if(sessionId){
            url=configPath.path + 'collect/getCollectPage?sessionId='+sessionId;
            store.dispatch(updateSessionId(sessionId));
        }else{
            url=configPath.path + 'collect/getCollectPage';
        }


        postData(url,dataJson).then((data) => {
            console.log(data);
            if (data == '50011') {
                window.location.href = configPath.path + 'wechatAuth?callbackUrl=' + encodeURIComponent(window.location.href);
                //return;
            }
            
            store.dispatch(updateStatus(data.status));
            switch (data.status) {
                case "success":
                    store.dispatch(updateMsg(data.message));
                    break;
                case "end":
                    store.dispatch(updateMsg(data.message));
                    break;
                case "unStart":
                    break;
                case "doing":
                    let {actFormListPojo,weixinConfig,min,max}=data;
                    if(actFormListPojo){
                        let list=[];
                        for(let i = 0;i<actFormListPojo.length;i++){
                            if(actFormListPojo[i].type=='uploadFile'){
                                store.dispatch(isFile(true));
                            }else if(actFormListPojo[i].type=='uploadImg'){
                                store.dispatch(isImg(true));
                            }else{
                                let item=actFormListPojo[i];
                                item.id = uuid.v1();;
                                item.value = '';
                                list.push(item);
                            }
                        }
                        store.dispatch(fetchUpdate(list));
                    }
  
                    if(weixinConfig){
                        store.dispatch({type:'UPDATE_WX',obj:weixinConfig});
                        let wxConfig=weixinConfig;
                        wxConfig.debug = false;
                        wxConfig.jsApiList = ["chooseImage","previewImage","uploadImage","downloadImage"];
                        wx.config(wxConfig);
                    }

                    store.dispatch({type:'UPDATE_MAX',num:max});
                    store.dispatch({type:'UPDATE_MIN',num:min});
                    store.dispatch(updateId(nextState.params.id));
                    console.log(store.getState());
                    //alert(JSON.stringify(store.getState()))
            }
        })
    }
}

const goWhichLottery=(nextState, replace)=>{
    document.title='抽奖活动';
    if (!!nextState.params.id) {
        let sessionId=nextState.location.query.sessionId||nextState.location.query[sessionIds], url='';
        let dataJson = {"prizeId": nextState.params.id};
        if(sessionId){
            url=configPath.path + 'prize/getPrizePage?sessionId='+sessionId;
            store.dispatch(updateSessionId(sessionId));
        }else{
            url=configPath.path + 'prize/getPrizePage';
        }

        postData(url,dataJson).then((data) => {
            console.log(data);
            if (data == '50011') {
                window.location.href = configPath.path + 'wechatAuth?callbackUrl=' + encodeURIComponent(window.location.href);
                //return;
            }
            store.dispatch(updateStatus(data.status));
            switch (data.status) {
                case "end":
                    store.dispatch(updateMsg(data.message));
                    break;
                case "unStart":
                    break;
                case "doing":
                    store.dispatch(updateId(nextState.params.id));
                    break;
            }
        });

    }
}

// store.dispatch(updateStatus('doing'));


render((
    <Provider store={store}>
        <Router history={hashHistory}>
            <Route path="/sign/:id"
             getComponent={
                                (nextState,callback)=>{
                                    require.ensure([],(require)=>{
                                        callback(null,require("./modules/SignApp").default)
                                    },"sign")
                                }} onEnter={goWhichSign}/>
            <Route path="/vote/:id" 
            getComponent={
                                (nextState,callback)=>{
                                    require.ensure([],(require)=>{
                                        callback(null,require('./modules/VoteApp').default)
                                    },"vote")
                                }} onEnter={goWhichVote}/>
            <Route path="/collect/:id"
            getComponent={
                                (nextState,callback)=>{
                                    require.ensure([],(require)=>{
                                        callback(null,require('./modules/CollectApp').default)
                                    },"collect")
                                }} onEnter={goWhichCollect}/>
            <Route path="/lottery/:id"
            getComponent={
                                (nextState,callback)=>{
                                    require.ensure([],(require)=>{
                                        callback(null,require('./modules/LotteryApp').default)
                                    },"lottery")
                                }} onEnter={goWhichLottery}/>
            <Route path="*" getComponent={
                                (nextState,callback)=>{
                                    require.ensure([],(require)=>{
                                        callback(null,require('./modules/NotFound').default)
                                    },"notFound")
                                }}/>
        </Router>
    </Provider>
), document.getElementById('app'));
