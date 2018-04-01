import React, {Component, PropTypes} from 'react';
import SignForm from './SignForm';
import EndPage from './EndPage';
import {connect} from 'react-redux';
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
          return (<SignForm {...this.props}/>)
        case "unStart":
          return (<UnStartPage {...this.props}/>)
        default:
          return (<div className='loading'><img src={configPath.staticPath+"static/img/loading.gif"} /></div>)
      }
    }
}

export default connect((state) => {return state})(App);
