describe('TestAccountCtrl', function () {

    var scope, modal;
    var controller, accountService;

    beforeEach(function () {
        module('bank');
    });

    beforeEach(inject(function ($controller, $rootScope, $modal, AccountService) {
        scope = $rootScope.$new();
        modal = $modal;
        accountService = AccountService;
        spyOn(accountService, 'findAll').and.callThrough();

        controller = $controller('AccountCtrl', {
            $scope: scope,
            $modal: modal,
            accountService: accountService
        });
    }));

    describe('Initialize', function () {
        it('load items', function () {
            expect(accountService.findAll).toHaveBeenCalledWith(scope.page, scope.perPage);
        });

        it('initialize page', function () {
            expect(scope.page).toBeDefined();
        });

        it('initialize perPage', function () {
            expect(scope.perPage).toBeDefined();
        });

    });

    describe('Test createAccount', function () {
        beforeEach(function () {
            spyOn(controller, 'createModalEdit').and.callThrough();
        });

        it('clear message of success', function () {
            scope.successMessage = 'OK';
            scope.createAccount();
            expect(scope.successMessage).toBeUndefined();
        });
        it('call createModalEdit function', function () {
            scope.createAccount();
            expect(controller.createModalEdit).toHaveBeenCalled();
        });
    });


    describe('Test editAccount', function () {
        beforeEach(function () {
            spyOn(controller, 'createModalEdit').and.callThrough();
        });

        it('clear message of success', function () {
            var account = {id: 1};
            scope.editAccount(account);
            expect(scope.successMessage).toBeUndefined();
        });
        it('call createModalEdit function', function () {
            var account = {id: 1};
            scope.editAccount(account);
            expect(controller.createModalEdit).toHaveBeenCalledWith(account);
        });
    });

    describe('Test removeAccount', function () {
        beforeEach(function () {
            spyOn(controller, 'confirmDialog').and.callThrough();
        });

        it('clear message of success', function () {
            var account = {id: 1};
            scope.removeAccount(account);
            expect(scope.successMessage).toBeUndefined();
        });
        it('call confirmDialog function', function () {
            var account = {id: 1};
            scope.removeAccount(account);
            expect(controller.confirmDialog).toHaveBeenCalledWith(account);
        });
    });
});