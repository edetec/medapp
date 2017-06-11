/**
 * Alerts Controller
 */

MedApp.controller('PatientCtrl', PatientCtrl);

function PatientCtrl($scope, PatientService, $ngBootbox) {
    $scope.patients = [];
    $scope.selected = null;
    $scope.name = '';
    
    $scope.newPatient = function(){
    	$scope.selected = null;
    	$scope.formPatient = {};
    };
    
    $scope.edit = function(patient){
    	$scope.selected = patient;
    	$scope.formPatient = angular.copy(patient);
    };
    
    $scope.save = function(){
    	if($scope.selected){
    		PatientService.update($scope.formPatient).success(function(data){
    			$ngBootbox.alert("Updated successfully!");
    			angular.copy($scope.formPatient, $scope.selected);
    		}).error(errorCallback);
    	}else{
			PatientService.save($scope.formPatient).success(function(data){
				$ngBootbox.alert("Saved successfully!");
				$scope.newPatient();
				$scope.fetch();
			}).error(errorCallback);
		}
		
    };
    
    $scope.delete = function(patient){
    	$ngBootbox.confirm("Are you sure you want to exclude the patient " + patient.name +"?")
        .then(function() {
            deletePatient(patient);
        });
    }

    var errorCallback = function(data, status, headers, config) {
        switch (status) {
        	case 400: {
        		console.log(data);
                $ngBootbox.alert(data.errorMessage);
                break;
            }
            case 500: {
                $ngBootbox.alert('Sorry, we had a problem. Try again later!');
                break;
            }
        }
    	console.log(data, status);
	};
	
    
    var deletePatient = function(patient){
		PatientService.delete(patient.cpf).success(
	       function(data) {
	          $scope.patients.splice($scope.patients.lastIndexOf(patient),1);
	       }).error(errorCallback);
    };
    
    $scope.fetch = function() {
        PatientService.getAll($scope.name).success(
           function(data, status, headers, config) {
              $scope.patients = data;
           }).error(errorCallback);
    };
    
    $scope.fetch();
}