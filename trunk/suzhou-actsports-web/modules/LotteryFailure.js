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
import {toastShowOff,updateStatus,fetchUpdate} from '../actions/toDoAction';
import {toggleVote,toggleOne,dialogShowOff,hasMoreUpdate,voteUpdate} from '../actions/voteAction';

require('../static/style/lotteryPage.css');
class LotteryPage extends Component{
    handleClick() {
      window.location.reload();
    }

    render(){
            let {dispatch} = this.props;
            let {handleClick} = this;
            return(
                <div id="lotteryFailure">
                    <div className='inner'>
                      <h2><img src={configPath.staticPath+'static/img/shibai.png'} /></h2>
                      <Button onClick={() => this.handleClick()}>再抽一次</Button>
                    </div>
                    <Toast showToast = {this.props.showToast} validateMsg={this.props.validateMsg} />
                </div>
                  )
              }
}


function select(state) {
      return{
          message:state.message,
          validateMsg:state.validateMsg,
          showToast:state.showToasts,
          enterID:state.enterID,
          sessionID:state.sessionID
      }
}

export default connect(select)(LotteryPage);
