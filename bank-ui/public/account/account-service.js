app.service('AccountService', ['$http', function ($http) {
    var ENDPOINT = Util.BANK_ACCOUNT_API;

    this.findAll = function (page, perPage) {
        var ajustPage = page-1;
        return $http.get(ENDPOINT+'?page='+ajustPage+'&perPage='+perPage);
    };

    this.save = function (newAccount) {
        return $http({
            method: 'POST',
            url: ENDPOINT,
            data: newAccount,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

    this.update = function (account) {
        delete account.createdAt;
        delete account.updatedAt;
        delete account.balance;
        delete account.status;
        return $http({
            method: 'PUT',
            url: ENDPOINT,
            data: account,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

    this.delete = function (id) {
        return $http({
            method: 'DELETE',
            url: ENDPOINT+'/'+id
        });
    };

    this.withdraw = function (withdrawRequest) {
        return $http({
            method: 'POST',
            url: ENDPOINT+'/withdraw',
            data: withdrawRequest,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

}]);