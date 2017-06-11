MedApp.factory("SpecialtyService", function($http) {
 	
 	var URI = "api/specialty/";
    var _getAll = function(description) {
    	var config = {}
    	if (description){
    		config.params = {'description': description};
    	}
        return $http.get(URI, config);
    };
 
    var _save = function(specialty) {
        return $http.post(URI, angular.toJson(specialty));
    };
 
    var _update = function(specialty) {
        return $http.put(URI + specialty.id, angular.toJson(specialty));
    };
 
    var _delete = function(id) {
        return $http.delete(URI + id);
    };
 
    return {
        getAll : _getAll,
        save : _save,
        update : _update,
        "delete": _delete,
    };
 
});