var app = angular.module("myApp", ['ngMaterial', 'ngMessages', 'mwl.calendar', 'ngAnimate', 'ui.bootstrap', 'colorpicker.module', 'material.svgAssetsCache']);



myApp.factory("UserInput", function(){

  var addtionalEvent = {};

  function getadditionalEvent() {
    return addtionalEvent;
  };

  function setadditionalEvent(newEvent){

    this.addtionalEvent = newEvent;
  };
});
