import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import Tip from '../components/Tip';
import configPath from '../config/dev';
require('../static/style/successPage.css');
// import 'weui';
class SuccessPage extends Component{
   render(){
            return(
                <div id="successPage">
        					<h2 className="title">
        						<img src={configPath.staticPath+"static/img/success_title.png"}/>
        						<Tip currentNum ={this.props.currentNum} message={this.props.message}/>
        					</h2>
        					<div className="mainContent">
        						<img src={configPath.staticPath+"static/img/success_body.png"}/>
        					</div>
        				</div>
                  )
      }
}
function select(state) {
      return{
            currentNum:state.currentNum,
            message:state.message
      }
}

export default connect(select)(SuccessPage)
