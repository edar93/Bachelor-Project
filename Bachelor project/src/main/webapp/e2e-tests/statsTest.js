'use strict';

describe('test of stats pages', function () {

    it('should redirect player to his stats', function () {
        browser.get('http://localhost:8090/port-royal/#!/login');
        browser.ignoreSynchronization = true;

        expect(browser.getLocationAbsUrl()).toMatch("/login");

        var pageTittle = element(by.id('loginLabel'));
        expect(pageTittle.getText()).toMatch('Zde se můžete přihlásit');

        element(by.model('userName')).sendKeys('marie');
        element(by.model('password')).sendKeys('adam');
        element(by.id('loginButton')).click();
        browser.sleep(2500);

        expect(browser.getLocationAbsUrl()).toMatch("/welcome");
        pageTittle = element(by.css('.mailLabel'));
        expect(pageTittle.getText()).toMatch('Vítej piráte marie');

        element(by.id('myStatsRedirection')).click();
        browser.sleep(2500);
        expect(browser.getLocationAbsUrl()).toMatch("/statslist/marie/1");
        var records = element.all(by.css('.statsRecord'));
        expect(records.count()).toBe(1);

        element(by.id('logoutButton')).click();
        browser.sleep(2500);

        var optionToLogin = element.all(by.id('toLoginPage'));
        expect(optionToLogin.count()).toBe(1);
    });

    it('should display players games records', function () {
        browser.get('http://localhost:8090/port-royal/#!/statslist/adam/1');
        browser.ignoreSynchronization = true;
        browser.sleep(2500);

        var pageTittle = element(by.id('loginLabel'));
        expect(pageTittle.getText()).toMatch('Hry hráče adam');

        expect(browser.getLocationAbsUrl()).toMatch("/statslist/adam/1");
        var records = element.all(by.css('.statsRecord'));
        expect(records.count()).toBe(3);

    });
});