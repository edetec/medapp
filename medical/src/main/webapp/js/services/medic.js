MedApp.factory("MedicService", function($http) {
 	
 	var URI = "api/medic/";
 	
 	var _getSpecialties = function(medic_id){
 		return $http.get(URI + medic_id + "/specialties/");
 	}
 	
    var _getAll = function(name, ordered) {
    	var config = { 'params':{}};
    	if (name){
    		config.params.name = name;
    	}
    	if (ordered){
    		config.params.ordered = ordered;
    	}
        return $http.get(URI, config);
    };
 
    var _save = function(medic) {
        return $http.post(URI, angular.toJson(medic));
    };
 
    var _update = function(medic) {
        return $http.put(URI + medic.id, angular.toJson(medic));
    };
 
    var _delete = function(id) {
        return $http.delete(URI + id);
    };
 
    return {
    	getSpecialties: _getSpecialties,
        getAll : _getAll,
        save : _save,
        update : _update,
        "delete": _delete,
    };
 
});