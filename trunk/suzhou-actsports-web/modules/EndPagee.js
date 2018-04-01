import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import Config from '../config/dev';
import {updateStatus} from '../actions/toDoAction';
require('../static/style/endPage.css');

// import 'weui';
class EndPage extends Component{

   render(){
         let {dispatch} = this.props;
            return(
               <div id="endPage">
                     <h2 className="title"><img src={Config.staticPath+ "static/img/end_title.png"}/></h2>
                     <div className="mainContent">
                           <img src={Config.staticPath+"static/img/end_body.png"}/>
                           <div className='tip'><p>{this.props.message}</p></div>
                     </div>
                     {window.location.href.indexOf('lottery')>-1?<p className = 'awardList'><span onClick={() => dispatch(updateStatus('record'))}>查看中奖纪录</span></p>:null}
               </div>
                  )
      }
}

function select(state) {
      return{
            message:state.message
      }
}

export default connect(select)(EndPage)
