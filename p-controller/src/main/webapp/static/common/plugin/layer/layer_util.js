/**
 *  Created by Hao Peng on 2017/5/11
 *  Layer弹出框封装
 *  官网 : http://layer.layui.com/
 *  API : http://www.layui.com/doc/modules/layer.html
 */
(function () {
  'use strict';

  window.L$ = LayerUtil;
  window.LayerUtil = LayerUtil;

  /**
   *  全局layer弹出层配置对象
   */
  window.layerConfig = {
    layerOpenSkin: 'layui-layer-lan2', //弹出层样式
    shadeClose: false, //点击弹出层周边遮罩，关闭弹出层
    scrollbar: true, //默认允许浏览器滚动，如果设定scrollbar: false，则屏蔽
    loadingSkin: 1, //loading层的样式
    isOutAnim: true, //默认情况下，关闭层时会有一个过度动画。如果你不想开启，设置 isOutAnim: false 即可
    zIndex: 10, //弹出层的zIndex
    resize: true //默认情况下，你可以在弹层右下角拖动来拉伸尺寸。如果对指定的弹层屏蔽该功能，
                 // 设置 false即可。该参数对loading、tips层无效
  }

  function LayerUtil() {
  }

  /**
   * layer基本弹出层封装
   * @param url 视图层请求路径
   * @param param 请求参数
   * @param config layer配置项
   */
  L$.layerOpenByUrl = function (url, param, config) {
    L$.layerLoad();
    /*$.extend(config,{area:[ '100%', '100%' ]})*/
    $.ajax({
      url: url,
      data: param,
      type:!config.method?'get':config.method,
      dataType: "html",
      success: function (html) {
        var _config = config || {};
        _config.content = html;
        L$.layerCloseLoad();
        L$.customLayerOpen(_config);
      }
    })
  }

  /**
   * layer弹出层自定义配置
   * 提供了较多的重要参数供定义，详情见文档
   * @param config
   */
  L$.customLayerOpen = function (config) {
    var options = {
      type: config.type || 1,
      title: config.title || ' ',
      area: config.area || ['700px', '700px'],
      offset: config.offset || '10px',
      scrollbar: config.scrollbar || window.layerConfig.scrollbar,
      shadeClose: config.shadeClose || window.layerConfig.shadeClose,
      skin: config.skin || window.layerConfig.layerOpenSkin,
      content: config.content || '',
      isOutAnim: config.isOutAnim || window.layerConfig.isOutAnim,
      resize: config.resize || window.layerConfig.resize,
      zIndex: config.zIndex || window.layerConfig.zIndex
    }
    if (config.cancel) {
      options.cancel = config.cancel;
    }
    if (config.btn) {
      options.btn = config.btn;
    }
    if (config.btn1) {
      options.btn1 = config.btn1;
    }
    if (config.btn2) {
      options.btn2 = config.btn2;
    }
    if (config.btn3) {
      options.btn3 = config.btn3;
    }
    layer.open(options);
  }

  /**
   * 遮罩层，在请求前添加，防止重复点击
   */
  L$.layerLoad = function (offset) {
    return layer.load(window.layerConfig.loadingSkin, {offset: offset});
  }

  /**
   * 关闭所有弹出层
   */
  L$.layerCloseAll = function () {
    layer.closeAll();
  }

  /**
   * 根据弹出层index关闭弹出层
   */
  L$.close = function (index) {
    layer.close(index);
  }

  /**
   * 仅关闭loading遮罩层
   */
  L$.layerCloseLoad = function () {
    layer.closeAll("loading");
  }

  /**
   * 成功提示
   * @param message 信息内容
   * @param callback 回调函数，内含参数为layer弹出层的index，可用layer.close(index)来关闭与其他交互
   */
  L$.layerAlertSuccess = function (message, callback, offset) {
    var option = {
      icon: 1,
      offset: offset
    }
    layer.alert(message, option, callback);
  }

  /**
   * 失败提示
   * @param message 信息内容
   * @param callback 回调函数，内含参数为layer弹出层的index，可用layer.close(index)来关闭与其他交互
   */
  L$.layerAlertFailed = function (message, callback, offset) {
    var option = {
      icon: 2,
      offset: offset
    }
    return layer.alert(message, option, callback);
  }

  /**
   * layer询问层
   * 如果yes和no默认不传入，则默认为关闭当前询问层
   * @param message 询问的信息内容
   * @param yes 确认按钮的回调 内含参数为layer弹出层的index，可用layer.close(index)来关闭与其他交互
   * @param no 取消按钮的回调 内含参数为layer弹出层的index，可用layer.close(index)来关闭与其他交互
   * @param option 额外的参数配置
   */
  L$.layerConfirm = function (message, yes, no, option) {
    var options ={
      icon:3
    };
    $.extend(options,option)
    layer.confirm(message, options, yes, no);
  }

  /**
   * 普通信息显示，在数秒内消失
   * @param message 信息内容
   * @param seconds 持续时间
   */
  L$.layerMsg = function (message, seconds, config) {
    var _config = {
      time: seconds || 6000
    };
    _config = $.extend({}, _config, config);
    layer.msg(message, _config);
  }

  /**
   * tips层的私有参数。支持上右下左四个方向，通过1-4进行方向设定。默认为右侧弹出
   * 如tips: 3则表示在元素的下面出现。
   * config = {
   *    tips : 1
   * }
   * @param message
   * @param dom
   * @param config
   */
  L$.layerTip = function (message, dom, config) {
    layer.tips(message, $(dom), config);
  }

  /**
   * 用于某些操作后的提示封装，要求data内含有status和message,
   * data = {
     *      'status' : 'true',
     *      'message' : '新增用户成功'
     * }
   * data = {
     *      'status' : 'false',
     *      'message' : '新增用户失败'
     * }
   * @param data
   * @param callback
   */
  L$.commonCallBack = function (data, callback, offset) {
    L$.layerCloseLoad();
    if (data["status"] == "true") {
      L$.layerAlertSuccess(data["message"], function () {
        layer.closeAll();
        if (callback) {
          callback();
        }
        /*else {
         B$.refreshThis();
         }*/
      }, offset)
    } else if (!data["message"]) {
      L$.layerAlertFailed("操作失败！", undefined, offset);
    } else {
      L$.layerAlertFailed(data["message"], undefined, offset);
    }
  }

})();