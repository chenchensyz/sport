import React, {Component, PropTypes} from 'react';
import configPath from '../config/dev';
require('../static/style/notFound.css');
class App extends Component {
    render() {
       return <div id='notFound'>
       		<img src={configPath.staticPath+'static/img/notFound1.png'} />
       </div>
    }
}

export default App;
