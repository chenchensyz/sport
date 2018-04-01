import React, { Component, PropTypes } from 'react';

class Tip extends Component {
  render() {
    if (this.props.currentNum) {
      return <p>您是第{this.props.currentNum}位报名者</p>
    }else if(this.props.message){
      return <p>{this.props.message}</p>
    } else {
      return null;
    }
  }
}

export default Tip