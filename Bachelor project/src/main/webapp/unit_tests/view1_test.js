'use strict';

describe('portRoyalApp.view1 module', function() {

  beforeEach(module('portRoyalApp.view1'));

  describe('view1 controller', function(){

    it('should ....', inject(function($controller) {
      //spec body

      //var test = Jasmine.createSpyObj();
      var view1Ctrl = $controller('View1Ctrl');
      expect(view1Ctrl).toBeDefined();
    }));

  });
});
