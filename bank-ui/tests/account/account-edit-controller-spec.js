describe('TestAccountEditCtrl', function () {
    var scope, modalInstance;
    var controller, accountService;
    var account;

    beforeEach(function () {
        module('bank');
    });

    beforeEach(inject(function ($controller, $rootScope, AccountService) {
        scope = $rootScope.$new();
        modalInstance = {};
        accountService = AccountService;

        account = {id: 1,
            agency: '1',
            number: '2',
            address: 'St 1',
            notesAmount10: 1,
            notesAmount20: 2,
            notesAmount50: 3,
            notesAmount100: 4
        };

        controller = $controller('AccountEditCtrl', {
            $scope: scope,
            $modalInstance: modalInstance,
            accountService: accountService,
            account: account
        });
    }));

    describe('Initialize', function () {
        it('variables', function () {
            expect(scope.errorMessage).toBeUndefined();
            expect(scope.accountChanged).toBeDefined();
            expect(scope.title).toBeDefined();
        });
    });

    describe('Private functions', function () {
        describe('handleError Test', function () {
            it('scope errorMessage equals err message when err message is defined', function () {
                var err = { message: 'Error message'};
                controller.handleError(err);
                expect(scope.errorMessage).toEqual(err.message);
            });

            it('set default message in scope errMessage when err is undefined', function () {
                controller.handleError(null);
                expect(scope.errorMessage).toBeDefined();
                expect(scope.errorMessage).not.toBeNull();
            });

            it('set default message in scope errMessage when err message is undefined', function () {
                var err = {code: 500};
                controller.handleError(err);
                expect(scope.errorMessage).toBeDefined();
                expect(scope.errorMessage).not.toBeNull();
            });
        });

        describe('save Test', function () {
            beforeEach(function () {
                spyOn(accountService, 'save').and.callThrough();
                spyOn(accountService, 'update').and.callThrough();
            });

            it('call account save when account is new', function () {
                var accountChanged = _.omit(account, 'id');
                scope.save(accountChanged);
                expect(accountService.save).toHaveBeenCalledWith(accountChanged);
                expect(accountService.update).not.toHaveBeenCalledWith(accountChanged);
            });

            it('call account update when account is not new', function () {
                scope.save(account);

                expect(accountService.save).not.toHaveBeenCalledWith(account);
                expect(accountService.update).toHaveBeenCalledWith(account);
            });

        });

        describe('cleanError Test', function () {
            it('clean error message', function () {
                scope.cleanError();
                expect(scope.errorMessage).toBeUndefined();
            });
        });

    });

});