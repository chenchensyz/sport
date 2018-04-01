import {combineReducers} from 'redux';
import {status,sessionID,message,enterID,validateMsg,showToasts, formList,currentNum,buttonToggle} from './toDoReducer';
import {voteChooseList,voteLimit,dialogShow,dialogMsg,hasMore} from './voteReducer';
import {textMin,textMax,weixinConfig,isImgShow,isFileShow,uploadImg,isOver,areaValue,textCount} from './collectReducer';

if (!Object.assign) {
    // 定义assign方法
  Object.defineProperty(Object, 'assign', {
    enumerable: false,
    configurable: true,
    writable: true,
    value: function(target) { // assign方法的第一个参数
      'use strict';
      // 第一个参数为空，则抛错
      if (target === undefined || target === null) {
        throw new TypeError('Cannot convert first argument to object');
      }

      var to = Object(target);
      // 遍历剩余所有参数
      for (var i = 1; i < arguments.length; i++) {
        var nextSource = arguments[i];
        // 参数为空，则跳过，继续下一个
        if (nextSource === undefined || nextSource === null) {
          continue;
        }
        nextSource = Object(nextSource);

        // 获取改参数的所有key值，并遍历
        var keysArray = Object.keys(nextSource);
        for (var nextIndex = 0, len = keysArray.length; nextIndex < len; nextIndex++) {
          var nextKey = keysArray[nextIndex];
          var desc = Object.getOwnPropertyDescriptor(nextSource, nextKey);
          // 如果不为空且可枚举，则直接浅拷贝赋值
          if (desc !== undefined && desc.enumerable) {
            to[nextKey] = nextSource[nextKey];
          }
        }
      }
      return to;
    }
  });
}


const acApp = combineReducers({textMin,textMax,buttonToggle,weixinConfig,isImgShow,isFileShow,uploadImg,isOver,textCount,areaValue,sessionID,hasMore,dialogMsg,voteLimit,dialogShow,voteChooseList,status, message,enterID,validateMsg,showToasts, formList,currentNum});
export default acApp;