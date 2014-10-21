directoryApp.controller('ContactController', ['$scope', 'Contact',
    function ($scope, Contact) {

        $scope.contacts = Contact.query();
        $scope.create = function () {
            Contact.save($scope.contact,
                function () {
                    $scope.contacts = Contact.query();
                    $('#saveContactModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.contact = Contact.get({id: id});
            $('#saveContactModal').modal('show');
        };

        $scope.delete = function (id) {
            Contact.delete({id: id},
                function () {
                    $scope.contacts = Contact.query();
                });
        };

        $scope.clear = function () {
            $scope.contact = {id: null, name: null, lastName: null, phoneNumber: null};
        };
    }]);
