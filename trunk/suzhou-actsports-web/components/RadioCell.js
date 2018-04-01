import React, {Component, PropTypes} from 'react';

class RadioCell extends Component {
    render() {
        return (
            <li className="mui-input-row mui-radio mui-left">
                <input name="radio" type="radio" onClick={this.props.onClick} id={this.props.code} disabled={!this.props.executable}/>
                <label htmlFor={this.props.code}>{this.props.text}</label>
            </li>
        );
    }
}


export default RadioCell;