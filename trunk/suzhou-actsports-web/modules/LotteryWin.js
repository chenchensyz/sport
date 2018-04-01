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
import FormList from '../components/FormList';
import formatForm from '../utils/voteForm';
import {toastShowOff,updateStatus,fetchUpdate,doValue,postData,updateMsg,buttonFlag} from '../actions/toDoAction';
import {toggleVote,toggleOne,dialogShowOff,hasMoreUpdate,voteUpdate} from '../actions/voteAction';

require('../static/style/lotteryPage.css');
class LotteryPage extends Component{
    handleClick() {
      let data = formatForm(this,this.props.formList),{dispatch,enterID,sessionID}=this.props;
      if(data){
        let url=sessionID?configPath.path + 'prize/savePrizeResult?sessionId='+sessionID:configPath.path + 'prize/savePrizeResult';
          data.prizeId = enterID;
          dispatch(buttonFlag(true));
          postData(url,data).then((data) => {
            if(data.status=='success'){
               dispatch(updateMsg(data.message));
               dispatch(updateStatus('success'));
            }
            if(data.status=='error'){
              toastShowOff(dispatch,data.message)
            }
            dispatch(buttonFlag(false));
          })
      }
    }

    render(){
            let {dispatch,message,buttonToggle} = this.props;
            let {handleClick} = this;
            return(
                <div id="lotteryWin">
                    <h2><img src={configPath.staticPath+'static/img/zhongjiang.png'} /></h2>
                    <div className="winMsg">
                        <h3>恭喜获得</h3>
                        <div className="winMsgContent">
                          <p>{message}</p>
                        </div>
                    </div>
                    {this.props.formList.length?
                      <div className="mui-card">
                        <div className="mui-card-content">
                              <div className="mui-card-content-inner">
                                    <FormList formLists={this.props.formList} handleChg={(id,text)=>dispatch(doValue(id,text))}/>
                                    {buttonToggle?
                                      <button disabled className="mui-btn mui-btn-block mui-btn-blue">提交信息</button>:
                                      <button onClick={() => this.handleClick()} className="mui-btn mui-btn-block mui-btn-blue">提交信息</button>
                                    }
                                    
                              </div>
                        </div>
                      </div>:null}
                    
                    <Toast showToast = {this.props.showToast} validateMsg={this.props.validateMsg} />
                </div>
                  )
              }
}


function select(state) {
      return{
          formList:state.formList,
          message:state.message,
          validateMsg:state.validateMsg,
          showToast:state.showToasts,
          enterID:state.enterID,
          sessionID:state.sessionID,
          buttonToggle:state.buttonToggle
      }
}

export default connect(select)(LotteryPage);
