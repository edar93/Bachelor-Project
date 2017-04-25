﻿'use strict';

describe('test of login action', function () {

    it('should login and logout user', function () {
        browser.get('http://localhost:8090/port-royal/#!/login');
        browser.ignoreSynchronization = true;

        expect(browser.getLocationAbsUrl()).toMatch("/login");

        var pageTittle = element(by.id('loginLabel'));
        expect(pageTittle.getText()).toMatch('Zde se můžete přihlásit');

        element(by.model('userName')).sendKeys('honza');
        element(by.model('password')).sendKeys('adam');
        element(by.id('loginButton')).click();
        browser.sleep(2500);

        expect(browser.getLocationAbsUrl()).toMatch("/welcome");
        pageTittle = element(by.css('.mailLabel'));
        expect(pageTittle.getText()).toMatch('Vítej piráte honza');

        element(by.id('logoutButton')).click();
        browser.sleep(2500);

        var optionToLogin = element.all(by.id('toLoginPage'));
        expect(optionToLogin.count()).toBe(1);
    });
});