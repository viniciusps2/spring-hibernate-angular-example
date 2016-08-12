app.controller('ConfirmDialogCtrl', ['$scope', '$modalInstance', 'message', 'okFunction', 'cancelFunction', function ($scope, $modalInstance, message, okFunction, cancelFunction) {

    $scope.message = message;

    $scope.ok = function () {
        if (okFunction) {
            okFunction();
        }
        $modalInstance.close();
    };

    $scope.cancel = function () {
        if (cancelFunction) {
            cancelFunction();
        }
        $modalInstance.dismiss();
    };
}]);