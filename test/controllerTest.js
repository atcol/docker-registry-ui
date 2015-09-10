describe('RegistryCtrl', function() {

    beforeEach(module('drwApp'));

    it('should create repositories model', inject(function ($controller) {
        var scope = {},
            ctrl = $controller('RegistryCtrl', {$scope: scope});
        expect(scope.repositories.length).toBeGreaterThan(0);
    }));
});
