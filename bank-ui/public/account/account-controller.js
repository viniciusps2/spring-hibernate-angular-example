app.controller('AccountCtrl', ['$scope', '$modal', 'AccountService', function ($scope, $modal, accountService) {
    var $controller = this;

    this.createModalEdit = function(account) {
        return $modal.open({
            animation: true,
            templateUrl: 'account/account-edit.html',
            controller: 'AccountEditCtrl',
            size: 'lg',
            resolve: {
                account: function() {
                    return account;
                }
            }
        });
    };

    $scope.createAccount = function() {
        $scope.successMessage = undefined;
        $controller.createModalEdit().result.then(function() {
            $scope.findAll(1, $scope.perPage);
            $scope.successMessage = 'Account created with success';
        }, function(err) {
            console.log(err);
        });
    };

    $scope.editAccount = function(account) {
        $scope.successMessage = undefined;
        $controller.createModalEdit(account).result.then(function() {
            $scope.findAll($scope.page, $scope.perPage);
            $scope.successMessage = 'Account updated with success';
        }, function(err) {
            console.log(err);
        });
    };

    $scope.removeAccount = function(account) {
        $scope.successMessage = undefined;
        $controller.confirmDialog(account).result.then(function() {
            $scope.successMessage = 'Account deleted with success';
        }, function(err) {
            console.log(err);
        });
    };

    this.confirmDialog = function(account) {
        var message = 'Are you sure to delete the Account # '+account.id+' ?';

        var okFunction = function () {
            accountService.delete(account.id).success(function () {
                $scope.findAll(1, $scope.perPage);
            }, function (err) {
                console.log(err);
            });
        };

        var cancelFunction;

        return $modal.open({
            animation: true,
            templateUrl: 'components/confirm-dialog/confirm-dialog.html',
            controller: 'ConfirmDialogCtrl',
            size: 'sm',
            resolve: {
                message: function () {
                    return message;
                },
                okFunction: function () {
                    return okFunction;
                },
                cancelFunction: function () {
                    return cancelFunction;
                }
            }
        });
    };

    $scope.findAll = function(page, perPage) {
        accountService.findAll(page, perPage).success(function (accounts) {
            $scope.accounts = accounts;
        }).error(function (err) {
            if (err && err.message) {
                $scope.errorMessage = err.message;
                console.error(err);
            } else {
                $scope.errorMessage = 'Error on request server';
                console.error($scope.errorMessage);
            }
        });
    };

    $scope.perPage = 50;
    $scope.page = 1;

    $scope.findAll(1, $scope.perPage);
}]);