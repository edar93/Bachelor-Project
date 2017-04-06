'use strict';

describe('port royal', function () {

  it('should login user', function () {
    browser.get('http://localhost:8090/port-royal/#!/login');
    browser.ignoreSynchronization = true;

    expect(browser.getLocationAbsUrl()).toMatch("/login");

    var pageTittle = element(by.id('loginLabel'));
    expect(pageTittle.getText()).toMatch('Zde se můžete přihlásit');

    element(by.model('userName')).sendKeys('adam1');
    element(by.model('password')).sendKeys('adam');
    element(by.id('loginButton')).click();
    browser.sleep(2500);

    expect(browser.getLocationAbsUrl()).toMatch("/welcome");

    element(by.id('logoutButton')).click();
    browser.sleep(2500);

    var optionToLogin = element.all(by.id('toLoginPage'));
    expect(optionToLogin.count()).toBe(1);

  });

  describe('face card', function () {
    beforeEach(function () {
      browser.get('http://localhost:8090/port-royal/#!/login');
      browser.ignoreSynchronization = true;

      expect(browser.getLocationAbsUrl()).toMatch("/login");

      var pageTittle = element(by.id('loginLabel'));
      expect(pageTittle.getText()).toMatch('Zde se můžete přihlásit');

      element(by.model('userName')).sendKeys('adam');
      element(by.model('password')).sendKeys('adam');
      element(by.id('loginButton')).click();
      browser.sleep(2500);
    });

    it('face card', function () {
      element(by.id('startGameButton')).click();
      browser.sleep(2500);

      element(by.id('CardPackage')).click();
      browser.sleep(2500);

      var facedCards = element.all(by.css('.card'));
      expect(facedCards.count()).toBe(1);
    });

    afterEach(function () {
      element(by.id('logoutButton')).click();
      browser.sleep(2500);

      var optionToLogin = element.all(by.id('toLoginPage'));
      expect(optionToLogin.count()).toBe(1);
    });

  });
});
