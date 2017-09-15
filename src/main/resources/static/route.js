angular.module('healthCare').config(function($routeProvider) {
	$routeProvider.when('/home', {
		templateUrl : 'pages/home.html',
		controller : 'homeController'
	}).when('/user', {
		templateUrl : 'pages/user.html',
		controller : 'userController'
	}).when('/adminLogin', {
		templateUrl : 'pages/admin/admin.html',
		controller : 'adminController'
	}).when('/category', {
		templateUrl : 'pages/admin/category.html',
		controller : 'adminController'
	}).when('/add_doctor', {
		templateUrl : 'pages/admin/add_doctor.html',
		controller : 'adminController'
	}).when('/view_doctors', {
		templateUrl : 'pages/admin/view_doctors.html',
		controller : 'adminController'
	}).when('/doctorLogin', {
		templateUrl : 'pages/doctor/doctor.html',
		controller : 'doctorController'
	}).when('/patientLogin', {
		templateUrl : 'pages/patient/patient.html',
		controller : 'patientController'
	}).when('/bookAppointment', {
		templateUrl : 'pages/patient/appointment.html',
		controller : 'patientController'
	}).otherwise({
		redirectTo : '/home'
	});
});
