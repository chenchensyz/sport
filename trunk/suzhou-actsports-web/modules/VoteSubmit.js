import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import 'whatwg-fetch';
import {doValue,toastShowOff,updateMsg,updateStatus,updateNum,postData,buttonFlag} from '../actions/toDoAction';
import FlexFigure from '../components/FlexFigure';
import FormList from '../components/FormList';
import Toast from '../components/Toast';
import configPath from '../config/dev';
import 'weui';
import 'react-weui/lib/react-weui.min.css';
import {Flex,FlexItem} from 'react-weui';
import voteForm from '../utils/voteForm';
require('../static/style/voteSubmit.css');

class voteSubmit extends Component{
    
    filterList(){
      let {dispatch,voteChooseList} = this.props;
      let voteList=voteChooseList.filter((item)=>{
              return item.checked==true;  
      });
      return voteList;
    }
    
    handleReSubmit() {
      window.location.reload();
    }

    handleSubmit(){
      let {dispatch,FormList,enterID,sessionID} = this.props;
      let data = voteForm(this,FormList);
      let choose= this.filterList(),listId=[];
      for(let i=0;i<choose.length;i++){
        listId.push(choose[i].id);
      }
      if(data){
        data.chooseList=listId;
        data.voteId=enterID;
        dispatch(buttonFlag(true));
        let url=sessionID?configPath.path + 'vote/index?sessionId='+sessionID:configPath.path + 'vote/index';
      postData(url,data).then((data) => {
            if(data.status=='success'){            
                dispatch(updateMsg(data.message));
                dispatch(updateNum(data.currentNum));
                dispatch(updateStatus(data.status));
              }
              if(data.status=='end'){
                toastShowOff(dispatch,'活动已经结束了');
                dispatch(updateStatus(data.status));
              }   
            dispatch(buttonFlag(false));     
          });
      }
      
    }

    render(){
            let {dispatch,buttonToggle} = this.props;
            return(
                <div id="voteSubmit">
                    <h2>您的选择</h2>
                    <div className="innerVote">
                      <FlexFigure lists={this.filterList()} />
                    </div>
                  <div className="mui-card">
                    <div className="mui-card-content">
                      <div className="mui-card-content-inner">
                      <FormList formLists={this.props.FormList} handleChg={(id,text)=>dispatch(doValue(id,text))}/>
                          <div className="btn-group">
                              <button className='reSubmit' onClick={()=>this.handleReSubmit()}>重新投票</button>       
                              {buttonToggle?
                                <button className='submitBtn' disabled>确定投票</button>:
                                <button className='submitBtn' onClick={()=>this.handleSubmit()}>确定投票</button>
                              }

                          </div>
                        
                      </div>
                    </div>
                  </div>
                  <Toast showToast = {this.props.showToast} validateMsg={this.props.validateMsg} />
                </div>
                  )
              }
}


function select(state) {
      return{
          voteChooseList:state.voteChooseList,
          FormList:state.formList,
          validateMsg:state.validateMsg,
          showToast:state.showToasts,
          enterID:state.enterID,
          sessionID:state.sessionID,
          buttonToggle:state.buttonToggle
      }
}

export default connect(select)(voteSubmit);
