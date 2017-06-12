/**
 * Alerts Controller
 */

MedApp.controller('MedicCtrl', MedicCtrl);

function MedicCtrl($scope, MedicService, SpecialtyService, $ngBootbox) {
    $scope.medics = [];
    $scope.selected = null;
    $scope.name = '';
    $scope.sortType = 'name'; 
	$scope.sortReverse  = false;
	
	$scope.sort = function(field){
		$scope.sortType = field; 
		$scope.sortReverse = !$scope.sortReverse
		
		$scope.fetch();
	};
    
    $scope.newMedic = function(){
    	$scope.selected = null;
    	$scope.formMedic = {};
    };
    
    $scope.reset = function(){
    	$scope.formMedic = angular.copy($scope.selected || {});
    };
    
    $scope.edit = function(medic){
    	MedicService.getSpecialties(medic.id).success(
           function(data, status, headers, config) {
		    	$scope.selected = medic;
		    	$scope.formMedic = angular.copy(medic);

	            $scope.formMedic.specialties = data;
           }).error(errorCallback);
    };
    
    $scope.save = function(){
    	if($scope.selected){
    		MedicService.update($scope.formMedic).success(function(data){
    			$ngBootbox.alert("Updated successfully!");
    			angular.copy($scope.formMedic, $scope.selected);
    		}).error(errorCallback);
    	}else{
			MedicService.save($scope.formMedic).success(function(data){
				$ngBootbox.alert("Saved successfully!");
				$scope.newMedic();
				$scope.fetch();
			}).error(errorCallback);
		}
		
    };
    
    $scope.delete = function(medic){
    	$ngBootbox.confirm("Are you sure you want to exclude the medic " + medic.description +"?")
        .then(function() {
            deleteMedic(medic);
        });
    }
    
    var getOrder = function(){
    	var order = $scope.sortReverse ? "":"-";
    	order += $scope.sortType;
    	return order;
    };

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
	
    
    var deleteMedic = function(medic){
		MedicService.delete(medic.id).success(
	       function(data) {
	          $scope.medics.splice($scope.medics.lastIndexOf(medic),1);
	       }).error(errorCallback);
    };
    
    $scope.fetch = function() {
        MedicService.getAll($scope.name, getOrder()).success(
           function(data, status, headers, config) {
              $scope.medics = data;
           }).error(errorCallback);
    };
    
    var fetchSpecialties = function() {
        SpecialtyService.getAll().success(
           function(data, status, headers, config) {
              $scope.specialties = data;
           }).error(errorCallback);
    };
    
    $scope.fetch();
    fetchSpecialties();
}