describe('TestAtmCtrl', function () {

    var scope, modal;
    var controller, atmService;

    beforeEach(function () {
        module('bank');
    });

    beforeEach(inject(function ($controller, $rootScope, $modal, AtmService) {
        scope = $rootScope.$new();
        modal = $modal;
        atmService = AtmService;
        spyOn(atmService, 'findAll').and.callThrough();

        controller = $controller('AtmCtrl', {
            $scope: scope,
            $modal: modal,
            atmService: atmService
        });
    }));

    describe('Initialize', function () {
        it('initialize variables', function () {
            expect(scope.page).toBeDefined();
            expect(scope.perPage).toBeDefined();
        });

        it('load items', function () {
            expect(atmService.findAll).toHaveBeenCalledWith(scope.page, scope.perPage);
        });
    });

    describe('Test createAtm', function () {
        beforeEach(function () {
            spyOn(controller, 'createModalEdit').and.callThrough();
        });

        it('clear message of success', function () {
            scope.successMessage = 'OK';
            scope.createAtm();
            expect(scope.successMessage).toBeUndefined();
        });

        it('call createModalEdit function', function () {
            scope.createAtm();
            expect(controller.createModalEdit).toHaveBeenCalled();
        });
    });

    describe('Test editAtm', function () {
        beforeEach(function () {
            spyOn(controller, 'createModalEdit').and.callThrough();
        });

        it('clear message of success', function () {
            var atm = {id: 1};
            scope.editAtm(atm);
            expect(scope.successMessage).toBeUndefined();
        });

        it('call createModalEdit function', function () {
            var atm = {id: 1};
            scope.editAtm(atm);
            expect(controller.createModalEdit).toHaveBeenCalledWith(atm);
        });
    });

    describe('Test removeAtm', function () {
        beforeEach(function () {
            spyOn(controller, 'confirmDialog').and.callThrough();
        });

        it('clear message of success', function () {
            var atm = {id: 1};
            scope.removeAtm(atm);
            expect(scope.successMessage).toBeUndefined();
        });

        it('call confirmDialog function', function () {
            var atm = {id: 1};
            scope.removeAtm(atm);
            expect(controller.confirmDialog).toHaveBeenCalledWith(atm);
        });
    });

    describe('Test requestWithdraw', function () {
        beforeEach(function () {
            spyOn(controller, 'createModalWithdraw').and.callThrough();
        });

        it('clear message of success', function () {
            scope.successMessage = 'OK';
            scope.requestWithdraw();
            expect(scope.successMessage).toBeUndefined();
        });

        it('call createModalEdit function', function () {
            scope.requestWithdraw();
            expect(controller.createModalWithdraw).toHaveBeenCalled();
        });
    });
});