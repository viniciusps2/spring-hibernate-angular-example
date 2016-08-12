var Util = (function () {
	function checkInjectedVariable (value, defaultValue) {
		return value === 'undefined' || value.indexOf('echo') >= 0 ? defaultValue : value;
	}

    return {
        BANK_ACCOUNT_API: checkInjectedVariable('/* @echo BANK_ACCOUNT_API */', 'http://localhost:3002/accounts'),

        BANK_ATM_API: checkInjectedVariable('/* @echo BANK_ATM_API */', 'http://localhost:3003/atms'),

        clone: function (obj) {
        	return JSON.parse(JSON.stringify(obj));
        }
    };
})();