import React, {Component, PropTypes} from 'react';

class CheckboxCell extends Component {
    render() {
        return (
            <li className="mui-input-row mui-checkbox mui-left">                
                <input name="checkbox" type="checkbox" id={this.props.code} onClick={this.props.onClick} disabled={!this.props.executable}/>
                <label htmlFor={this.props.code}>{this.props.text}</label>
            </li>
        );
    }
}

export default CheckboxCell;