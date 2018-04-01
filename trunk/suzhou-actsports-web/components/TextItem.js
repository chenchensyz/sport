import React, {Component, PropTypes} from 'react';

class TextItem extends Component {
    render() {
        return (
            <div>
                <h5>{this.props.title+'：'}</h5>
                <div className="mui-input-row">
                        <input type="text" ref={this.props.name} onChange={(e)=>this.props.onChange(this.props.name,e.target.value)}/>
                </div>
            </div>
        );
    }
}

TextItem.propTypes={
	title:PropTypes.string.isRequired,
    name:PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired
}

export default TextItem;