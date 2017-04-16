﻿﻿
'use strict';

describe('player in game action', function () {
    beforeEach(function () {
        browser.get('http://localhost:8090/port-royal/#!/login');
        browser.ignoreSynchronization = true;

        expect(browser.getLocationAbsUrl()).toMatch("/login");

        var pageTittle = element(by.id('loginLabel'));
        expect(pageTittle.getText()).toMatch('Zde se můžete přihlásit');

        element(by.model('userName')).sendKeys('lenka');
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

    it('should join and left game', function () {
        element(by.buttonText("Připojit se do hry")).click();
        browser.sleep(2500);
        expect(browser.getLocationAbsUrl()).toMatch("/gamecreation");

        element(by.buttonText("Opustit hru")).click();
        browser.sleep(2500);
        expect(browser.getLocationAbsUrl()).toMatch("/welcome");
    });

    it('should create own game and left it	', function () {
        element(by.buttonText("Založit vlastní hru")).click();
        browser.sleep(2500);
        expect(browser.getLocationAbsUrl()).toMatch("/gamecreation");

        element(by.buttonText("Opustit hru")).click();
        browser.sleep(2500);
        expect(browser.getLocationAbsUrl()).toMatch("/welcome");
    });
});