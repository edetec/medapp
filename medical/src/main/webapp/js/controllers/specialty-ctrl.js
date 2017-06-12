/**
 * Alerts Controller
 */

MedApp.controller('SpecialtyCtrl', SpecialtyCtrl);

function SpecialtyCtrl($scope, SpecialtyService, $ngBootbox) {
    $scope.specialties = [];
    $scope.selected = null;
    $scope.description = '';
    
    $scope.newSpecialty = function(){
    	$scope.selected = null;
    	$scope.formSpecialty = {};
    };
    
    $scope.edit = function(specialty){
    	$scope.selected = specialty;
    	$scope.formSpecialty = angular.copy(specialty);
    };
    
    $scope.save = function(){
    	if($scope.selected){
    		SpecialtyService.update($scope.formSpecialty).success(function(data){
    			$ngBootbox.alert("Updated successfully!");
    			angular.copy($scope.formSpecialty, $scope.selected);
    		}).error(errorCallback);
    	}else{
			SpecialtyService.save($scope.formSpecialty).success(function(data){
				$ngBootbox.alert("Saved successfully!");
				$scope.newSpecialty();
				$scope.fetch();
			}).error(errorCallback);
		}
		
    };
    
    $scope.delete = function(specialty){
    	$ngBootbox.confirm("Are you sure you want to exclude the specialty " + specialty.description +"?")
        .then(function() {
            deleteSpecialty(specialty);
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
	
    
    var deleteSpecialty = function(specialty){
		SpecialtyService.delete(specialty.id).success(
	       function(data) {
	          $scope.specialties.splice($scope.specialties.lastIndexOf(specialty),1);
	       }).error(errorCallback);
    };
    
    $scope.fetch = function() {
        SpecialtyService.getAll($scope.description).success(
           function(data, status, headers, config) {
              $scope.specialties = data;
           }).error(errorCallback);
    };
    
    $scope.fetch();
}