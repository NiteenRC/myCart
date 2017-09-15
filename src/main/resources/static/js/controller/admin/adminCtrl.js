angular.module('healthCare.admin_module',
		[ 'healthCare.shared_module.sharedService' ]).controller(
		'adminController', adminController);

function adminController($scope, sharedService) {
	'use strict';

	var categoryUrl = "/category";
	var productUrl = "/doctor";

	getCategory();
	$scope.addCategory = addCategory;
	$scope.removeCategory = removeCategory;

	$scope.saveDoctor = saveDoctor;

	$scope.categoryData = {
		categoryName : null
	};

	$scope.doctorData = {
		doctorID : null,
		doctorName : null,
		address : null,
		mobileNumber : null,
		email : null,
		image : null
	};

	function addCategory() {
		if (!sharedService.isDefinedOrNotNull($scope.categoryData.categoryName)) {
			return alert('Please enter category name')
		}

		sharedService.postMethod(categoryUrl, $scope.categoryData).then(
				function(response) {
					getCategory();
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

	function removeCategory(id) {
		sharedService.deleteMethod(categoryUrl + "/" + id).then(
				function(response) {
					getCategory();
					$scope.successMessage = 'User created successfully';
					$scope.errorMessage = '';
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong';
					$scope.successMessage = '';
				});
	}

	function saveDoctor() {

		if (!sharedService.isDefinedOrNotNull($scope.doctorData.doctorName)) {
			return alert('Please add Doctor name')
		}

		if (!sharedService.isDefinedOrNotNull($scope.doctorData.mobileNumber)) {
			return alert('Please add mobile number')
		} else if (isNaN($scope.doctorData.mobileNumber)) {
			return alert('Price should be number')
		}

		if (!sharedService.isDefinedOrNotNull($scope.item)) {
			return alert('Please select category')
		}

		if (!sharedService.isDefinedOrNotNull($scope.doctorData.image)) {
			return alert('Please add image')
		}

		var fd = new FormData();
		fd.append('file', $scope.doctorData.image);
		fd.append('doctorData', JSON.stringify($scope.doctorData));

		sharedService.uploadFile(productUrl + "/" + $scope.item.categoryID, fd)
				.then(function(response) {
					alert('Doctor saved successfully!!')
					getDoctors();
				}, function(error) {
					alert(error.data.errorMessage);
				});
	}

	$scope.getDoctors = getDoctors;

	var doctorUrl = "doctor";

	function getDoctors() {
		sharedService.getAllMethod(doctorUrl).then(function(response) {
			$scope.doctors = response.data;
		}, function(error) {
			$scope.errorMessage = 'Some thing went wrong';
			$scope.successMessage = '';
		});
	}

	$scope.removeDoctor = removeDoctor;

	function removeDoctor(id) {
		sharedService.deleteMethod(doctorUrl + "/" + id).then(
				function(response) {
					getDoctors();
				}, function(error) {
					$scope.errorMessage = 'Some thing went wrong';
					$scope.successMessage = '';
				});
	}
}