import React, { Component, PropTypes } from 'react';

class Toast extends Component {
  render() {
    if (this.props.validateMsg) {
    		return <div className ={this.props.showToast?"mui-toast-container mui-active":"mui-toast-container"} ><div className="mui-toast-message">{this.props.validateMsg}</div></div>      

    } else {
      return null;
    }
  }
}

export default Toast