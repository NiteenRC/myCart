angular.module('healthCare.patient_module',
		[ 'healthCare.shared_module.sharedService' ]).controller(
		'patientController', patientController);

function patientController($scope, $rootScope, sharedService) {
	'use strict';

	var categoryUrl = "/category";
	var doctorUrl = "/doctor";
	var appointmentUrl = "/appointment";

	getCategory();
	$scope.bookAppointment = bookAppointment;
	$scope.fetchDoctorsByCategoryID = fetchDoctorsByCategoryID;

	$scope.appointment = {
		doctor : null,
		patientID : null
	};

	function bookAppointment() {
		if (!sharedService.isDefinedOrNotNull($scope.doctor)) {
			return alert('Please enter doctor name')
		}

		$scope.appointment.doctor = $scope.doctor.doctorID;
		$scope.appointment.patientID = parseInt($rootScope.userID)

		sharedService.postMethod(appointmentUrl, $scope.appointment).then(
				function(response) {
					// getCategory();
				}, function(error) {
					alert(error.data.errorMessage);
				});
	}

	function getCategory() {
		sharedService.getAllMethod(categoryUrl).then(function(response) {
			$scope.categories = response.data;
		}, function(error) {
			$scope.errorMessage = 'Some thing went wrong';
			$scope.successMessage = '';
		});
	}

	function fetchDoctorsByCategoryID(categoryID) {
		sharedService.getAllMethod(doctorUrl + "/by/" + categoryID).then(
				function(response) {
					$scope.doctors = response.data;
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong' + error;
					$scope.successMessage = '';
				});
	}

	// Date picker
	$scope.clear1 = function() {
		$scope.fromDate = null;
	};

	$scope.open1 = function($event) {
		$event.preventDefault();
		$event.stopPropagation();

		$scope.opened1 = true;
	};

	$scope.dateOptions = {
		formatYear : 'yy',
		startingDay : 1
	};

	$scope.formats = [ 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate' ];
	$scope.format = $scope.formats[2];
}