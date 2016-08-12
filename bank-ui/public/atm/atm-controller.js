app.controller('AtmCtrl', ['$scope', '$modal', 'AtmService', function ($scope, $modal, atmService) {
    var $controller = this; // using to unit test

    this.createModalEdit = function(atm) {
        return $modal.open({
            animation: true,
            templateUrl: 'atm/atm-edit.html',
            controller: 'AtmEditCtrl',
            size: 'lg',
            resolve: {
                atm: function() {
                    return atm;
                }
            }
        });
    };

    this.createModalWithdraw = function(atmId) {
        return $modal.open({
            animation: true,
            templateUrl: 'atm/withdraw.html',
            controller: 'WithdrawCtrl',
            size: 'lg',
            resolve: {
                atmId: function() {
                    return atmId;
                }
            }
        });
    };

    $scope.requestWithdraw = function(atmId) {
        $scope.successMessage = undefined;
        $controller.createModalWithdraw(atmId).result.then(function() {
            $scope.findAll(1, $scope.perPage);
            $scope.successMessage = 'Withdraw accomplished with success on ATM # ' + atmId;
        });
    };

    $scope.createAtm = function() {
        $scope.successMessage = undefined;
        $controller.createModalEdit().result.then(function() {
            $scope.findAll(1, $scope.perPage);
            $scope.successMessage = 'ATM created with success';
        }, function(err) {
            console.log(err);
        });
    };

    $scope.editAtm = function(atm) {
        $scope.successMessage = undefined;
        $controller.createModalEdit(atm).result.then(function() {
            $scope.findAll($scope.page, $scope.perPage);
            $scope.successMessage = 'ATM updated with success';
        }, function(err) {
            console.log(err);
        });
    };

    $scope.removeAtm = function(atm) {
        $scope.successMessage = undefined;
        $controller.confirmDialog(atm).result.then(function() {
            $scope.successMessage = 'ATM deleted with success';
        }, function(err) {
            console.log(err);
        });
    };

    this.confirmDialog = function(atm) {
        var message = 'Are you sure to delete the ATM '+atm.id+' ?';

        var okFunction = function () {
            atmService.delete(atm.id).success(function () {
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
        atmService.findAll(page, perPage).success(function (atms) {
            $scope.atms = atms;
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