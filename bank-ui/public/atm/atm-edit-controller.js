app.controller('AtmEditCtrl', ['$scope', '$modalInstance', 'AtmService', 'atm', function ($scope, $modalInstance, atmService, atm) {

    var $controller = this;

    this.initializeVariables = function(atm) {
        atm = atm || {};
        $scope.errorMessage = undefined;
        $scope.atmChanged = Util.clone(atm);
        $scope.title = atm.id ? 'Update ATM #' + atm.id : 'New ATM';
    };

    this.handleError = function(err) {
        if (err && err.message) {
            $scope.errorMessage = err.message;
        } else {
            $scope.errorMessage = 'Error on request server';
        }
    };

    $scope.save = function (atmChanged) {
        // Update
        if (atmChanged.id) {
            atmService.update(atmChanged).success(function() {
                $modalInstance.close();
            }).error(function(err) {
                $controller.handleError(err);
            });
        } else { // Create
            atmService.save(atmChanged).success(function() {
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

    $controller.initializeVariables(atm);

}]);