import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import CollectPage from './CollectPage';
import EndPage from './EndPage';
import SuccessPage from './SuccessPage';
import UnStartPage from './unStartPage';

import configPath from '../config/dev';

class App extends Component {
    render() {
      switch (this.props.status) {
        case "success":
          return (<SuccessPage {...this.props}/>)
        case "end":
          return (<EndPage {...this.props}/>)
        case "doing":
          return (<CollectPage {...this.props}/>)
        case "unStart":
          return (<UnStartPage {...this.props}/>)
        default:
          return (<div className='loading'><img src={configPath.staticPath+"static/img/loading.gif"} /></div>)
      }
    }
}

export default connect((state) => {return state})(App);
