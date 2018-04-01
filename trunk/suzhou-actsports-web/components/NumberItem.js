import React, {Component, PropTypes} from 'react';

class NumberItem extends Component {
    render() {
        return (
            <div>
                <h5>{this.props.title+'：'}</h5>
                <div className="mui-input-row">
                        <input type="tel" ref={this.props.name} onChange={(e)=>this.props.onChange(this.props.name,e.target.value)}/>
                </div>
            </div>
        );
    }
}

NumberItem.propTypes={
    title:PropTypes.string.isRequired,
    name:PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired
}

export default NumberItem;