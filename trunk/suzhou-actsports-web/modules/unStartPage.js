import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import configPath from '../config/dev';
require('../static/style/unStartPage.css');
// import 'weui';
class unStartPage extends Component{
   render(){
            return(
                <div id="unStartPage">
					<h2 className="title">
						<img src={configPath.staticPath+"static/img/unStart_title.png"}/>
					</h2>
					<div className="mainContent">
						<img src={configPath.staticPath+"static/img/unStart_body.png"}/>
					</div>
				</div>
                  )
      }
}


export default unStartPage
