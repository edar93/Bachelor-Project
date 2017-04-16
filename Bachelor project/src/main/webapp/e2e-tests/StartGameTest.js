'use strict';

describe('player in game action', function () {
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

    afterEach(function () {
        element(by.id('logoutButton')).click();
        browser.sleep(2500);

        var optionToLogin = element.all(by.id('toLoginPage'));
        expect(optionToLogin.count()).toBe(1);
    });

    it('should start game and face card', function () {
        element(by.id('startGameButton')).click();
        browser.sleep(2500);

        var facedCards = element.all(by.css('.card'));
        expect(facedCards.count()).toBe(0);

        element(by.id('CardPackage')).click();
        browser.sleep(2500);

        facedCards = element.all(by.css('.card'));
        expect(facedCards.count()).toBe(1);
    });
});