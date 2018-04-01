import React, {Component, PropTypes} from 'react';

class TextareaItem extends Component {
    render() {
        return (
            <div>
                <h5>{this.props.title+' :'}</h5>
                <div className="mui-input-row">
                    <textarea placeholder=""  ref={this.props.name}  onChange={(e)=>this.props.onChange(this.props.name,e.target.value)}></textarea>
                </div>
            </div>
        );
    }
}


export default TextareaItem;