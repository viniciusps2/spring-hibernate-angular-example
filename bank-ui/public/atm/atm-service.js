app.service('AtmService', ['$http', function ($http) {
    var ENDPOINT = Util.BANK_ATM_API;

    this.findAll = function (page, perPage) {
        var ajustPage = page-1;
        return $http.get(ENDPOINT+'?page='+ajustPage+'&perPage='+perPage);
    };

    this.save = function (newAtm) {
        return $http({
            method: 'POST',
            url: ENDPOINT,
            data: newAtm,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    };

    this.update = function (atm) {
        delete atm.createdAt;
        delete atm.updatedAt;
        delete atm.balance;
        delete atm.status;
        return $http({
            method: 'PUT',
            url: ENDPOINT,
            data: atm,
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