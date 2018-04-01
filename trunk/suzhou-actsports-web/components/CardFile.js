



import React, {Component, PropTypes} from 'react';

// 上传图片组件
class FormList extends Component {
    render() {
        return (
              <div className="mui-card file-card">
                    <div className="mui-card-content">
                          <div className="mui-card-content-inner">
                             <h5>上传文本附件</h5>
                              <ul>
                                <li>
                                  <img src={'static/img/vote_item.png'} />
                                  <span className='del'><i className="mui-icon mui-icon-closeempty"></i></span>
                                </li>
                                <li className='upload uploadFile'>
                                  +
                                </li>
                              </ul>
                          </div>
                    </div>
              </div>  
        );
    }
}

export default FormList;