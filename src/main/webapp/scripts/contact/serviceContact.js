directoryApp.factory('Contact', ['$resource',
    function ($resource) {
        return $resource('app/rest/contacts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    }]);
