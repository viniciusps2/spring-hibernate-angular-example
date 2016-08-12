describe('TestAtmEditCtrl', function () {
    var scope, modalInstance;
    var controller, atmService;
    var atm;

    beforeEach(function () {
        module('bank');
    });

    beforeEach(inject(function ($controller, $rootScope, AtmService) {
        scope = $rootScope.$new();
        modalInstance = {};
        atmService = AtmService;

        atm = {id: 1,
            agency: '1',
            number: '2',
            address: 'St 1',
            notesAmount10: 1,
            notesAmount20: 2,
            notesAmount50: 3,
            notesAmount100: 4
        };

        controller = $controller('AtmEditCtrl', {
            $scope: scope,
            $modalInstance: modalInstance,
            atmService: atmService,
            atm: atm
        });
    }));
 
    describe('Initialize functions call and variables', function () {
        it('undefined errorMessage', function () {
            expect(scope.errorMessage).toBeUndefined();
        });

        it('initialize atmChanged', function () {
            expect(scope.atmChanged).toBeDefined();
        });

        it('initialize title', function () {
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
                spyOn(atmService, 'save').and.callThrough();
                spyOn(atmService, 'update').and.callThrough();
            });

            it('call atm save when atm is new', function () {
                var atmChanged = _.omit(atm, 'id');
                scope.save(atmChanged);
                expect(atmService.save).toHaveBeenCalledWith(atmChanged);
                expect(atmService.update).not.toHaveBeenCalledWith(atmChanged);
            });

            it('call atm update when atm is not new', function () {
                scope.save(atm);

                expect(atmService.save).not.toHaveBeenCalledWith(atm);
                expect(atmService.update).toHaveBeenCalledWith(atm);
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