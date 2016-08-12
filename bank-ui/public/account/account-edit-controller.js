app.controller('AccountEditCtrl', ['$scope', '$modalInstance', 'AccountService', 'account', function ($scope, $modalInstance, accountService, account) {

    var $controller = this;

    account = account || {};
    $scope.errorMessage = undefined;
    $scope.accountChanged = Util.clone(account);
    $scope.title = account.id ? 'Update Account #' + account.id : 'New Account';

    this.handleError = function(err) {
        if (err && err.message) {
            $scope.errorMessage = err.message;
        } else {
            $scope.errorMessage = 'Error on request server';
        }
    };

    $scope.save = function (account) {
        // Update
        if (account.id) {
            accountService.update(account).success(function() {
                $modalInstance.close();
            }).error(function(err) {
                $controller.handleError(err);
            });
        } else { // Create
            accountService.save(account).success(function() {
                $modalInstance.close();
            }).error(function(err) {
                $controller.handleError(err);
            });
        }
    };

    $scope.cancel = function () {
        $modalInstance.dismiss();
    };

    $scope.cleanError = function() {
        $scope.errorMessage = undefined;
    };

}]);