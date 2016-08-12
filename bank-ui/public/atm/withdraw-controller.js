app.controller('WithdrawCtrl', ['$scope', '$modalInstance', 'AtmService', 'atmId', function ($scope, $modalInstance, atmService, atmId) {

    var $controller = this;

    this.initializeVariables = function(atmId) {
        $scope.errorMessage = undefined;
        $scope.withdrawRequest = {id: atmId};
    };

    this.handleError = function(err) {
        if (err && err.message) {
            $scope.errorMessage = err.message;
        } else {
            $scope.errorMessage = 'Error on request server';
        }
    };

    $scope.ok = function (withdrawRequest) {
        atmService.withdraw(withdrawRequest).success(function() {
            $modalInstance.close();
        }).error(function(err) {
            $controller.handleError(err);
        });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss();
    };

    $scope.cleanError = function() {
        $scope.errorMessage = undefined;
    };

    $controller.initializeVariables(atmId);

}]);