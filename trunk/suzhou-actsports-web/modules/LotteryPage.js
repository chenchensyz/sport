import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import validator from 'validator';
import 'whatwg-fetch';
import ReactPullLoad from 'react-pullload';
import Toast from '../components/Toast';
import configPath from '../config/dev';
import 'weui';
import 'react-weui/lib/react-weui.min.css';
import formatForm from '../utils/voteForm';
import {Dialog,TabBar,Button} from 'react-weui';
import {toastShowOff,updateStatus,fetchUpdate,postData,updateMsg} from '../actions/toDoAction';
import {isImg} from '../actions/collectAction';
require('../static/style/lotteryPage.css');
class LotteryPage extends Component{
    handleClick() {
        let {dispatch,enterID,sessionID} = this.props,data={};        
        dispatch(isImg(true));
        data.prizeId=enterID;
        let url=sessionID?configPath.path + 'prize/index?sessionId='+sessionID:configPath.path + 'prize/index';
        postData(url,data).then((data) => {
              dispatch(isImg(false));
              if(data.status=='failure'){
                  dispatch(updateStatus('failure'));
              }
              if(data.status=='done'){
                  toastShowOff(dispatch,data.message);
              }
              if(data.status=='win'){
                  let formList = data.actFormListPojo.map(function(item, index) {
                        item.id = 'formList' + index;
                        item.chooseList = item.entryChooseList;
                        item.value = '';
                        return item;
                  });
                  dispatch(fetchUpdate(formList));
                  dispatch(updateMsg(data.message));
                  dispatch(updateStatus('win'));
              }
                      
            });
    }

    render(){
            let {dispatch,isImgShow} = this.props;
            let {handleClick} = this;
            return(
                <div id="lotteryPage">
                    <div className='inner'>
                      <h2><img src={configPath.staticPath+'static/img/choujiang.png'}/></h2>
                      {isImgShow?
                        <Button disabled>点击抽奖</Button>:
                        <Button onClick={() => this.handleClick()}>点击抽奖</Button>}
                    </div>
                    {isImgShow?<div className='loadingDiv'><img src={configPath.staticPath+'static/img/loading1.gif'} /></div>:null}
                    <Toast showToast = {this.props.showToast} validateMsg={this.props.validateMsg} />
                </div>
                  )
              }
}


function select(state) {
      return{
          isImgShow:state.isImgShow,
          message:state.message,
          validateMsg:state.validateMsg,
          showToast:state.showToasts,
          enterID:state.enterID,
          sessionID:state.sessionID
      }
}

export default connect(select)(LotteryPage);
