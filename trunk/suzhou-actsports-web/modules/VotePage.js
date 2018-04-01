import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import validator from 'validator';
import 'whatwg-fetch';
import ReactPullLoad from 'react-pullload';
import Toast from '../components/Toast';
import configPath from '../config/dev';
import 'weui';
import 'react-weui/lib/react-weui.min.css';
import {Dialog,TabBar,Button} from 'react-weui';
import {toastShowOff,updateStatus,fetchUpdate,postData} from '../actions/toDoAction';
import {toggleVote,toggleOne,dialogShowOff,hasMoreUpdate,voteUpdate} from '../actions/voteAction';
import Votelist from '../components/votelist';

require('../static/style/votePage.css');

var pageNum=1;

class VotePage extends Component{
    handleClick() {
        let {dispatch,voteChooseList,voteLimit} = this.props;
        let voteList=voteChooseList.filter((item)=>{
            return item.checked==true;  
        });
        if(voteList.length==0){
          toastShowOff(dispatch,'您还没有投票');
          return;
        }
        if(voteList.length>voteLimit){
          toastShowOff(dispatch,'本次最多可以投'+voteLimit+'票');
          return;
        }
        dispatch(updateStatus('submit'));

    }

    handleClose(){
      let {dispatch,dialogShow} = this.props;
      if(dialogShow){
        dialogShowOff(dispatch,'',false);
      }
      
    }

    handleChoose(id){
      let {dispatch,voteChooseList,voteLimit} = this.props;
      if(voteLimit==1){
        dispatch(toggleOne(id));
      }else{
        dispatch(toggleVote(id));
      }
      
    }

    handleOpen(msg){
      let {dispatch} = this.props;
      dialogShowOff(dispatch,msg,true);
    }
    //shuxin
    onRefresh(resolve,reject){
      resolve();
    }

    //加载更多
    loadMore(resolve){
      let {dispatch,voteChooseList,enterID,sessionID} = this.props;
      let actFormListPojo=[],formList=[],newFormList=[];

      let dataJson = {"voteId": enterID,"pageNum":++pageNum,"pageSize":12};
      let url=sessionID?configPath.path + 'vote/getVotePage?sessionId='+sessionID:configPath.path + 'vote/getVotePage';

      postData(url,dataJson).then((data) => {
          console.log(data)
                  actFormListPojo = data.actFormListPojo.filter(function (item) {
                      return item.type=='list';
                  });

                  if(actFormListPojo[0].voteChooseList.length){
                    formList = actFormListPojo[0].voteChooseList.map(function(item, index) {
                        item.value = '';
                        return item;
                    });
                    newFormList = voteChooseList.concat(formList);                    
                    dispatch(voteUpdate(newFormList));                
                    resolve();
                  }else{
                    resolve();
                    dispatch(hasMoreUpdate(false));  
                  }
          });
    }

    render(){
            let {dispatch,hasMore,message} = this.props;
            let {handleClick} = this;
            return(
                <div id="votePage">
                      <h2>{message}</h2>
                      <ReactPullLoad distanceBottom={160} hasMore={hasMore}  downEnough={150}  onRefresh={this.onRefresh.bind(this)} onLoadMore={this.loadMore.bind(this)}>
                        <Votelist handleOpen={(msg)=>this.handleOpen(msg)} voteLimit={this.props.voteLimit} voteChooseList={this.props.voteChooseList} handleChoose={(id)=> {this.handleChoose(id)}}/>
                      </ReactPullLoad>
                     <TabBar><Button onClick={() => this.handleClick()}>提交投票</Button></TabBar>
                      <Dialog title={this.props.dialogMsg} show={this.props.dialogShow} onClick={()=>this.handleClose()} />
                      <Toast showToast = {this.props.showToast} validateMsg={this.props.validateMsg} />
                </div>
                  )
              }
}


function select(state) {
      return{
          voteChooseList:state.voteChooseList,
          voteLimit:state.voteLimit,
          message:state.message,
          validateMsg:state.validateMsg,
          showToast:state.showToasts,
          dialogMsg:state.dialogMsg,
          dialogShow:state.dialogShow,
          hasMore:state.hasMore,
          enterID:state.enterID,
          sessionID:state.sessionID
      }
}

export default connect(select)(VotePage);
