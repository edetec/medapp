var MedApp = angular.module('MedApp', ['ui.bootstrap', 'ui.router', 'ngCookies', 'ngBootbox']);

MedApp.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        // For unmatched routes
        $urlRouterProvider.otherwise('/');

        // Application routes
        $stateProvider
            .state('index', {
                url: '/',
                templateUrl: 'templates/home.html'
            })
            .state('patient', {
                url: '/patient',
                templateUrl: 'templates/patient.html'
            })
            .state('medic', {
                url: '/medic',
                templateUrl: 'templates/medic.html'
            })
            .state('specialty', {
                url: '/specialty',
                templateUrl: 'templates/specialty.html'
            });
    }
]);